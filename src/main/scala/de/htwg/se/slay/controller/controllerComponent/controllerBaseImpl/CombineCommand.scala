package de.htwg.se.slay.controller.controllerComponent.controllerBaseImpl

import de.htwg.se.slay.model.gamepieceComponent._
import de.htwg.se.slay.util.Command

class CombineCommand(c1:Int, c2: Int, ctrl:Controller) extends Command{
  var memento1: GamePiece = ctrl.grid(c1).gamepiece
  var memento2: GamePiece = ctrl.grid(c2).gamepiece

  override def doStep(): Unit = {
    memento1 = ctrl.grid(c1).gamepiece
    memento2 = ctrl.grid(c2).gamepiece

    ctrl.grid(c1).territory.removeUnit(memento1.asInstanceOf[UnitGamePiece])
    ctrl.grid(c2).territory.removeUnit(memento2.asInstanceOf[UnitGamePiece])

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

    ctrl.grid(c1).territory.addUnit(ctrl.grid(c1).gamepiece.asInstanceOf[UnitGamePiece])
    if(memento1.asInstanceOf[UnitGamePiece].hasMoved || memento2.asInstanceOf[UnitGamePiece].hasMoved)
      ctrl.grid(c1).gamepiece.asInstanceOf[UnitGamePiece].hasMoved = true
  }

  override def undoStep(): Unit = {
    val tmp_mem1 = ctrl.grid(c1).gamepiece
    val tmp_mem2 = ctrl.grid(c2).gamepiece
    ctrl.grid(c1).gamepiece = memento1
    ctrl.grid(c2).gamepiece = memento2

    ctrl.grid(c1).territory.removeUnit(tmp_mem1.asInstanceOf[UnitGamePiece])
    ctrl.grid(c1).territory.addUnit(memento1.asInstanceOf[UnitGamePiece])
    ctrl.grid(c2).territory.addUnit(memento2.asInstanceOf[UnitGamePiece])

    memento1 = tmp_mem1
    memento2 = tmp_mem2
  }

  override def redoStep(): Unit = {
    val tmp_mem1 = ctrl.grid(c1).gamepiece
    val tmp_mem2 = ctrl.grid(c2).gamepiece
    ctrl.grid(c1).gamepiece = memento1
    ctrl.grid(c2).gamepiece = memento2

    ctrl.grid(c1).territory.addUnit(memento1.asInstanceOf[UnitGamePiece])
    ctrl.grid(c1).territory.removeUnit(tmp_mem1.asInstanceOf[UnitGamePiece])
    ctrl.grid(c2).territory.removeUnit(tmp_mem2.asInstanceOf[UnitGamePiece])

    memento1 = tmp_mem1
    memento2 = tmp_mem2
  }
}
