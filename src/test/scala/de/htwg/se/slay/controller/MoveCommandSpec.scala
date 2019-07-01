package de.htwg.se.slay.controller

import de.htwg.se.slay.model.Peasant
import org.scalatest.{Matchers, WordSpec}

class MoveCommandSpec extends WordSpec with Matchers{
  "A MoveCommand is used to move a UnitGamePieces and" should{
    val ctrl = new Controller
    ctrl.createGrid("Map1")
    BuyCommand(8, ctrl).doStep()

    "move a Unit from one field to another " in{
      new MoveCommand(ctrl.grid(8), ctrl.grid(24), ctrl).doStep()
      //ctrl.grid(24).gamepiece should be (classOf[Peasant])
    }
  }
}
