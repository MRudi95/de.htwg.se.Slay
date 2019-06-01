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

  def seeBalance(c: Int): Unit ={
    if(checkOwner(c))
      notifyObservers(new BalanceEvent(grid(c).territory.capital.balance))
  }


  private def checkOwner(c: Int):Boolean ={
    if(grid(c).owner == players(state)) true
    else {notifyObservers(OwnerErrorEvent()); false}
  }

  private def checkNoPiece(idx: Int): Boolean ={
    grid(idx).gamepiece match {
      case _:NoPiece => true
      case _:Tree => true
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
        notifyObservers(UnitErrorEvent())
        false
    }
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
