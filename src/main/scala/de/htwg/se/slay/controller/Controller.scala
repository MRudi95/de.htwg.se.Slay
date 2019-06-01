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
    notifyObservers()
  }


  def checkOwner(c: Int):Boolean ={
    if(grid(c).owner == players(state)) true
    else {notifyObservers(OwnerErrorEvent()); false}
  }

  def checkBalance(c: Int): Unit ={
    if(checkOwner(c))
      notifyObservers(new BalanceEvent(grid(c).territory.capital.balance))
  }

  def checkNoPiece(idx: Int): Boolean ={
    if(grid(idx).gamepiece.isInstanceOf[NoPiece] || grid(idx).gamepiece.isInstanceOf[Tree]) true
    else {notifyObservers(GamePieceErrorEvent()); false}
  }

//  def buyPeasant(c: Int): Unit ={
//    if(checkOwner(c) && grid(c).territory.capital.balance >= 10) {
//      grid(c).territory.capital.balance -= 10
//      grid(c).gamepiece = new Peasant(grid(c).owner)
//      notifyObservers()
//    } else notifyObservers(MoneyErrorEvent())
//  }

  def buyPeasant(c: Int): Unit ={
    if(checkOwner(c) && grid(c).territory.capital.balance >= 10){
      undoManager.doStep(BuyCommand(c, this))
      notifyObservers()
    } else notifyObservers(MoneyErrorEvent())
  }

  def placeCastle(c: Int): Unit ={
    if (checkOwner(c) && grid(c).territory.capital.balance >= 15) {
      undoManager.doStep(CastleCommand(c, this))
      notifyObservers()
    } else notifyObservers(MoneyErrorEvent())
  }

  def moneymoney(): Unit = {
    for(field <- capitals){
      val cap = field.gamepiece.asInstanceOf[Capital]
      cap.balance += field.territory.size()
    }
  }

  def nextturn(): Unit = {
    StatePlayerTurn.handle(nextplayer.next(), this)
  }

  def turnPlayer(p: Int): Unit ={
    state = p
    undoManager.reset()
    notifyObservers(new PlayerEvent(players(p).name))
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
}
