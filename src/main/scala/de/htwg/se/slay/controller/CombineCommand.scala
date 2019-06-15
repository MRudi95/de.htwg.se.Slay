package de.htwg.se.slay.controller

import de.htwg.se.slay.model._
import de.htwg.se.slay.util.Command

class CombineCommand(c1:Int, c2: Int, ctrl:Controller) extends Command{
  var memento1: GamePiece = ctrl.grid(c1).gamepiece
  var memento2: GamePiece = ctrl.grid(c2).gamepiece

  override def doStep(): Unit = {
    memento1 = ctrl.grid(c1).gamepiece
    memento2 = ctrl.grid(c2).gamepiece

    (ctrl.grid(c1).gamepiece, ctrl.grid(c2).gamepiece) match {
      case (_: Peasant, _: Peasant) =>
        ctrl.grid(c1).gamepiece = new Spearman(ctrl.grid(c1).owner)
        ctrl.grid(c2).gamepiece = NoPiece()
      case (_: Peasant, _: Spearman) | (_: Spearman, _: Peasant) =>
        ctrl.grid(c1).gamepiece = new Knight(ctrl.grid(c1).owner)
        ctrl.grid(c2).gamepiece = NoPiece()
      case (_: Peasant, _: Knight) | (_: Knight, _: Peasant) =>
        ctrl.grid(c1).gamepiece = new Baron(ctrl.grid(c1).owner)
        ctrl.grid(c2).gamepiece = NoPiece()
      case (_: Spearman, _: Spearman) =>
        ctrl.grid(c1).gamepiece = new Baron(ctrl.grid(c1).owner)
        ctrl.grid(c2).gamepiece = NoPiece()
      case _ =>
    }
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
