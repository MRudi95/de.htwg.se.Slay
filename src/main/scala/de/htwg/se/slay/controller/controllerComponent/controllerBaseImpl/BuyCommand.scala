package de.htwg.se.slay.controller.controllerComponent.controllerBaseImpl

import de.htwg.se.slay.model.gamepieceComponent.{GamePiece, Peasant, UnitGamePiece}
import de.htwg.se.slay.util.Command

case class BuyCommand(c: Int, ctrl:Controller) extends Command{
  val memento: GamePiece = ctrl.grid(c).gamepiece
  override def doStep(): Unit = {
    ctrl.grid(c).territory.capital.balance -= 10
    ctrl.grid(c).gamepiece = new Peasant(ctrl.grid(c).owner)
    ctrl.grid(c).territory.addUnit(ctrl.grid(c).gamepiece.asInstanceOf[UnitGamePiece])
  }

  override def undoStep(): Unit = {
    ctrl.grid(c).territory.capital.balance += 10
    ctrl.grid(c).territory.removeUnit(ctrl.grid(c).gamepiece.asInstanceOf[UnitGamePiece])
    ctrl.grid(c).gamepiece = memento
  }

  override def redoStep(): Unit = {
    doStep()
  }
}
