package de.htwg.se.slay.controller

import de.htwg.se.slay.model.{Peasant, Player}
import org.scalatest.{Matchers, WordSpec}

class BuyCommandSpec extends WordSpec with Matchers{
  "A BuyCommand is used to purchase a Peasant and" should{
    val controller = new Controller
    controller.createGrid("Test", "test")

    "place a Peasant on a Field given by an Index" in{
      BuyCommand(6, controller).doStep()
      controller.grid(6).gamepiece.getClass should be(classOf[Peasant])
    }

    "save the old GamePiece" in{
      val bc: BuyCommand = BuyCommand(5, controller)
      val gp = controller.grid(5).gamepiece
      bc.doStep()
      bc.memento should be (gp)
    }

    "undo and redo the Command" in{
      val bc: BuyCommand = BuyCommand(5, controller)
      val gp = controller.grid(5).gamepiece
      bc.doStep()
      bc.memento should be (gp)

      bc.undoStep()
      controller.grid(5).gamepiece should be (bc.memento)

      bc.redoStep()
      controller.grid(5).gamepiece.getClass should be(classOf[Peasant])
    }
  }
}
