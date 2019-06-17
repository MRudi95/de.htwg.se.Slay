package de.htwg.se.slay.controller

import de.htwg.se.slay.model.{GamePiece, NoPiece}
import de.htwg.se.slay.util.Command

class MoveCommand(c1:Int, c2: Int, ctrl:Controller) extends Command{
  var memento1: GamePiece = ctrl.grid(c1).gamepiece
  var memento2: GamePiece = ctrl.grid(c2).gamepiece

  override def doStep(): Unit = {
    memento1 = ctrl.grid(c1).gamepiece
    memento2 = ctrl.grid(c2).gamepiece

    ctrl.grid(c1).gamepiece = NoPiece()
    ctrl.grid(c2).gamepiece = memento1
  }

  override def undoStep(): Unit = {
    val tmp_mem1 = ctrl.grid(c1).gamepiece
    val tmp_mem2 = ctrl.grid(c2).gamepiece
    ctrl.grid(c1).gamepiece = memento1
    ctrl.grid(c2).gamepiece = memento2
    memento1 = tmp_mem1
    memento2 = tmp_mem2
  }

  override def redoStep(): Unit = {
    undoStep()
  }
}