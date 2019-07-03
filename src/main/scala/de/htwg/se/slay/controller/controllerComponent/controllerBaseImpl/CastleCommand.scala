package de.htwg.se.slay.controller.controllerComponent.controllerBaseImpl

import de.htwg.se.slay.model.gamepieceComponent.{Castle, GamePiece}
import de.htwg.se.slay.util.Command

case class CastleCommand(c: Int, ctrl:Controller) extends Command{
  val memento: GamePiece = ctrl.grid(c).gamepiece
  override def doStep(): Unit = {
    ctrl.grid(c).territory.capital.balance -= 15
    ctrl.grid(c).gamepiece = Castle(ctrl.grid(c).owner)
  }

  override def undoStep(): Unit = {
    ctrl.grid(c).territory.capital.balance += 15
    ctrl.grid(c).gamepiece = memento
  }

  override def redoStep(): Unit = {
    doStep()
  }
}
