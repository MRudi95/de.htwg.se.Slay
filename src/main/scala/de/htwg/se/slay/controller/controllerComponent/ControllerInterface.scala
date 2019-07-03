package de.htwg.se.slay.controller.controllerComponent

import de.htwg.se.slay.model.gridComponent.{FieldInterface, GridInterface}
import de.htwg.se.slay.model.playerComponent.Player
import de.htwg.se.slay.util.Observable

import scala.collection.immutable.HashSet

trait ControllerInterface extends Observable{

  def grid:GridInterface
  def capitals: HashSet[FieldInterface]
  def players: Vector[Player]

  def gridToString: String
  def addPlayer(player: Player): Unit
  def changeName(name: String, player: Int): Unit

  def createGrid(mapname: String, typ: String = "main"): Unit
  def seeBalance(c: Int): Unit

  def buyPeasant(c: Int): Unit
  def placeCastle(c: Int): Unit
  def combineUnit(c1: Int, c2: Int): Unit
  def moveUnit(c1: Int, c2: Int): Unit

  def surrender():Unit

  def moneymoney(): Unit
  def nextturn(): Unit

  def undo(): Unit
  def redo(): Unit
}



trait Event{}

case class SuccessEvent() extends Event


case class WelcomeEvent() extends Event
case class ReadPlayerEvent(player:Int) extends Event
case class GridCreatedEvent() extends Event


case class PlayerEvent(name: String) extends Event
case class BalanceEvent(bal: Int, inc: Int, cost: Int) extends Event
case class VictoryEvent(name: String) extends Event


case class MoneyErrorEvent() extends Event
case class OwnerErrorEvent() extends Event
case class GamePieceErrorEvent() extends Event
case class CombineErrorEvent() extends Event
case class MoveErrorEvent(reason: String = "") extends Event
case class MovableErrorEvent() extends Event
case class MovedErrorEvent() extends Event

case class UndoErrorEvent() extends Event
case class RedoErrorEvent() extends Event