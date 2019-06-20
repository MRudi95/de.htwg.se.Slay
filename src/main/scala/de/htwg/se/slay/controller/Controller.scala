package de.htwg.se.slay.controller

import de.htwg.se.slay.model._
import de.htwg.se.slay.util.{Observable, UndoManager}

import scala.collection.immutable.HashSet

class Controller extends Observable{
  var players: Vector[Player] = Vector(Player("Player0", "\033[104m"))
  var grid: Grid = _
  var capitals: HashSet[Field] = _

  val playerturn: List[PlayerTurn] = List(Player1Turn(), Player2Turn(), Player0Turn())
  val nextplayer: Iterator[PlayerTurn] = Iterator.continually(playerturn).flatten
  var state: Int = 1

  val undoManager = new UndoManager


  def gridToString: String = grid.toString

  def addPlayer(player: Player): Unit = players = players :+ player

  def createGrid(mapname: String, typ: String = "main"): Unit = {
    val (g, c) = new SquareMapBuilder(players).gridCreator(mapname, typ)
    grid = g
    capitals = c
    notifyObservers(GridCreatedEvent())
  }

  def seeBalance(c: Int): Unit ={
    if(checkOwner(c))
      notifyObservers(BalanceEvent(grid(c).territory.capital.balance))
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
    if(grid(c).territory.capital.balance >= bal) true
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
    if(grid(c2).neighbors.exists(x => x.territory == grid(c1).territory)) true
    else {moveErrorMsg = "Not a neighboring field!"; false}
  }

  private def checkMoveStrength(c1: Int, c2:Int): Boolean ={
    if(grid(c2).gamepiece.strength < grid(c1).gamepiece.strength
      && !grid(c2).neighbors.exists(x => x.territory == grid(c2).territory
      && x.gamepiece.strength >= grid(c1).gamepiece.strength)) true
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
    }
  }




  def moneymoney(): Unit = {
    for(field <- capitals){
      val cap = field.gamepiece.asInstanceOf[Capital]
      cap.balance += field.territory.size
      cap.balance -= field.territory.armyCost
      if(cap.balance <= 0){
        cap.balance = 0
        field.territory.fields.foreach(x => x.gamepiece match{
          case _:UnitGamePiece =>
            field.territory.removeUnit(_)
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
      case gp:UnitGamePiece => gp.hasMoved = false
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
      case 1 => notifyObservers(VictoryEvent(players(state+1).name))
      case 2 => notifyObservers(VictoryEvent(players(state-1).name))
      case _ =>
    }
  }
}
