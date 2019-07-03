package de.htwg.se.slay.controller.controllerComponent

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


