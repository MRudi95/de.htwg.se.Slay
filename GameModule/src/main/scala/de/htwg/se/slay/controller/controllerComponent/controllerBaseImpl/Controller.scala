package de.htwg.se.slay.controller.controllerComponent.controllerBaseImpl

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpEntity, HttpMethods, HttpRequest, HttpResponse}
import akka.http.scaladsl.server.Directives.as
import akka.http.scaladsl.unmarshalling.Unmarshal
import net.codingwell.scalaguice.InjectorExtensions._
import com.google.inject.{Guice, Injector}
import de.htwg.se.slay.SlayModule
import de.htwg.se.slay.controller.controllerComponent._
import de.htwg.se.slay.model.fileIOComponent.FileIOInterface
import de.htwg.se.slay.model.gamepieceComponent._
import de.htwg.se.slay.model.gridComponent.{FieldFactory, FieldInterface, GridFactory, GridInterface}
import de.htwg.se.slay.model.mapComponent.MapFactory
import de.htwg.se.slay.model.playerComponent.Player
import de.htwg.se.slay.util.UndoManager
import play.api.libs.json.{JsObject, Json}

import scala.collection.immutable.HashSet
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContextExecutor, Future}
import scala.util.{Failure, Success}

class Controller extends ControllerInterface{
  private val envPlayerModuleHost = sys.env.getOrElse("PLAYER_MODULE_HOST", "localhost:9002")
  val playerModuleServiceUrl = s"http://$envPlayerModuleHost/"
  implicit val actorSystem: ActorSystem = ActorSystem("actorSystemController")
  implicit val executionContext: ExecutionContextExecutor = actorSystem.dispatcher

  var players: Vector[Player] = Vector(
    new Player("Player0", "\u001b[104m"),
    new Player("Player 1", "\u001b[103m"),
    new Player("Player 2", "\u001b[102m"))

  var capitals: HashSet[FieldInterface] = _

  val playerturn: List[PlayerTurn] = List(Player1Turn(), Player2Turn(), Player0Turn())
  val nextplayer: Iterator[PlayerTurn] = Iterator.continually(playerturn).flatten
  var state: Int = 1

  val undoManager = new UndoManager
  val injector: Injector = Guice.createInjector(new SlayModule)

  var grid: GridInterface = injector.instance[GridFactory].create(Vector(injector.instance[FieldFactory].create(players(0))), 0, 0) //for Web Tech, so Grid isnt empty at first

  def gridToString: String = grid.toString(players)

  def addPlayer(player: Player): Unit = players = players :+ player

  def changeName(name: String): String = {
//    players(player).name = name
//    if(player == state) notifyObservers(PlayerEvent(players(player).name))
    val id = 1234
    val json = Json.obj(
      "name" -> name
    ).toString()

    val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(
      HttpMethods.PUT,
      uri = playerModuleServiceUrl + s"player/$id",
      entity = HttpEntity.apply(json)
    ))
    val responseStringFuture = responseFuture.flatMap(res => Unmarshal(res.entity).to[String])
    val responseString = Await.result(responseStringFuture, Duration("10s"))

    responseString
  }

  def createGrid(mapname: String, typ: String = "main"): Unit = {
    val (g, c) = injector.instance[MapFactory].create(players).gridCreator(mapname, typ)
    grid = g
    capitals = c
    notifyObservers(GridCreatedEvent())
  }

  def seeBalance(c: Int): Unit ={
    val ter = grid(c).territory match {
      case Some(value) => value
    }
    if(checkOwner(c) && ter.capital != null){
      notifyObservers(BalanceEvent(ter.capital.balance, ter.size, ter.armyCost))
    }
  }



  private def checkOwner(c: Int):Boolean ={
    if(grid(c).owner == players(state)) true
    else {notifyObservers(OwnerErrorEvent()); false}
  }

  private def checkNoPiece(idx: Int): Boolean ={
    grid(idx).gamepiece match {
      case _:NoPiece => true
      case _:Tree => true
      case _:Grave => true
      case _ =>
        notifyObservers(GamePieceErrorEvent())
        false
    }
  }

  private def checkBalance(c: Int, bal:Int): Boolean ={
    val ter = grid(c).territory match {
      case Some(value) => value
    }
    if(ter.capital.balance >= bal) true
    else {notifyObservers(MoneyErrorEvent()); false}
  }

  private def checkCombine(c1: Int, c2: Int): Boolean ={
    (grid(c1).gamepiece, grid(c2).gamepiece) match {
      case (_: Peasant, _: Peasant) => true
      case (_: Peasant, _: Spearman) | (_: Spearman, _: Peasant) => true
      case (_: Peasant, _: Knight) | (_: Knight, _: Peasant) => true
      case (_: Spearman, _: Spearman) => true
      case _ =>
        notifyObservers(CombineErrorEvent())
        false
    }
  }

  private var moveErrorMsg: String = ""
  private def checkMove(c1: Int, c2: Int): Boolean ={
      if(checkMoveOwner(c1, c2) && checkMoveNeighbor(c1, c2) && checkMoveStrength(c1, c2)
        || checkMoveTerritory(c1, c2)) true
      else {notifyObservers(MoveErrorEvent(moveErrorMsg)); false}
  }

  private def checkMoveTerritory(c1: Int, c2:Int): Boolean =
    grid(c1).territory == grid(c2).territory && checkNoPiece(c2)

  private def checkMoveOwner(c1: Int, c2:Int): Boolean =
    grid(c2).owner != players(0) && grid(c2).owner != grid(c1).owner

  private def checkMoveNeighbor(c1: Int, c2:Int): Boolean ={
    if(grid(c2).neighbors.exists{
      case Some(field) => field.territory == grid(c1).territory
      case None => false
      }
    ) true
    else {moveErrorMsg = "Not a neighboring field!"; false}
  }

  private def checkMoveStrength(c1: Int, c2:Int): Boolean ={
    if(grid(c2).gamepiece.strength < grid(c1).gamepiece.strength
      && !grid(c2).neighbors.exists{
      case Some(field) =>
        field.territory == grid(c2).territory && field.gamepiece.strength >= grid(c1).gamepiece.strength
      case None => false
      }
    ) true
    else {moveErrorMsg = "Your Unit is too weak!"; false}
  }

  private def checkMovable(c: Int): Boolean ={
    if(grid(c).gamepiece.isInstanceOf[UnitGamePiece]) true
    else {notifyObservers(MovableErrorEvent()); false}
  }

  private def checkMoved(c: Int): Boolean ={
    if(!grid(c).gamepiece.asInstanceOf[UnitGamePiece].hasMoved) true
    else {notifyObservers(MovedErrorEvent()); false}
  }



  def buyPeasant(c: Int): Unit ={
    if(checkOwner(c) && checkNoPiece(c) && checkBalance(c, 10)){
      undoManager.doStep(BuyCommand(c, this))
      notifyObservers()
    }
  }

  def placeCastle(c: Int): Unit ={
    if (checkOwner(c) && checkNoPiece(c) && checkBalance(c, 15)) {
      undoManager.doStep(CastleCommand(c, this))
      notifyObservers()
    }
  }

  def combineUnit(c1: Int, c2: Int): Unit = {
    if(checkOwner(c1) && checkOwner(c2) && checkCombine(c1, c2)) {
      undoManager.doStep(new CombineCommand(c1, c2, this))
      notifyObservers()
    }
  }

  def moveUnit(c1: Int, c2: Int): Unit = {
    if(checkOwner(c1) && checkMovable(c1) && checkMoved(c1) && checkMove(c1, c2)){
      undoManager.doStep(new MoveCommand(grid(c1), grid(c2), this))
      notifyObservers()
      checkWin()
    }
  }


  def checkWin(): Unit = {
    if(grid.forall(f => f.owner == players(0) || f.owner == players(state))){
      state match {
        case 1 =>
          notifyObservers(VictoryEvent(players(state+1).name))
        case 2 =>
          notifyObservers(VictoryEvent(players(state-1).name))
        case _ =>
      }
    }
  }




  def moneymoney(): Unit = {
    for(field <- capitals){
      val cap = field.gamepiece.asInstanceOf[Capital]
      val ter = field.territory match {
        case Some(value) => value
      }
      cap.balance += ter.size
      cap.balance -= ter.armyCost
      if(cap.balance < 0){
        cap.balance = 0
        ter.fields.foreach(x => x.gamepiece match{
          case unit:UnitGamePiece =>
            ter.removeUnit(unit)
            x.gamepiece = Grave()
          case _ =>
        })
        notifyObservers()
      }
    }
  }

  def nextturn(): Unit = {
    StatePlayerTurn.handle(nextplayer.next(), this)
  }

  def turnPlayer(p: Int): Unit ={
    state = p
    undoManager.reset()
    grid.foreach(_.gamepiece match{
      case gp:UnitGamePiece => gp.copyTo(false)
      case _ =>
    })
    notifyObservers()
    notifyObservers(PlayerEvent(players(p).name))
  }


  def undo():Unit = {
    if(undoManager.undoStep())
      notifyObservers()
    else
      notifyObservers(UndoErrorEvent())
  }

  def redo():Unit = {
    if(undoManager.redoStep())
      notifyObservers()
    else
      notifyObservers(RedoErrorEvent())
  }


  def surrender():Unit = {
    state match {
      case 1 =>
        grid.foreach(f => if(f.owner != players(0)) f.owner = players(state+1))
        notifyObservers()
        notifyObservers(VictoryEvent(players(state+1).name))
      case 2 =>
        grid.foreach(f => if(f.owner != players(0)) f.owner = players(state-1))
        notifyObservers()
        notifyObservers(VictoryEvent(players(state-1).name))
      case _ =>
    }
  }


  def save(file: String): Unit ={
    val fileIO = injector.getInstance(classOf[FileIOInterface])
    fileIO.save(file, players, state, grid)
  }

  def load(file: String): Unit ={
    val fileIO = injector.getInstance(classOf[FileIOInterface])
    val (p1name, p2name, s, g) = fileIO.load(file, players)
    players(1).name = p1name
    players(2).name = p2name
    state = s
    grid = g
    notifyObservers()
    notifyObservers(PlayerEvent(players(state).name))
  }
}
