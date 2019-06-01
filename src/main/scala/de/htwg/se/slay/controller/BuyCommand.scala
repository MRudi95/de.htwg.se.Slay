package de.htwg.se.slay.controller

import de.htwg.se.slay.model.{GamePiece, Peasant}
import de.htwg.se.slay.util.Command

case class BuyCommand(c: Int, ctrl:Controller) extends Command{
  val memento: GamePiece = ctrl.grid(c).gamepiece
  override def doStep(): Unit = {
    ctrl.grid(c).territory.capital.balance -= 10
    ctrl.grid(c).gamepiece = new Peasant(ctrl.grid(c).owner)
  }

  override def undoStep(): Unit = {
    ctrl.grid(c).territory.capital.balance += 10
    ctrl.grid(c).gamepiece = memento
  }

  override def redoStep(): Unit = {
    doStep()
  }
}
