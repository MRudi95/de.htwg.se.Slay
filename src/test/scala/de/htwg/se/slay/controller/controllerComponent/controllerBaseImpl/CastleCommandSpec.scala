package de.htwg.se.slay.controller.controllerComponent.controllerBaseImpl

import de.htwg.se.slay.model.gamepieceComponent.Castle
import org.scalatest.{Matchers, WordSpec}

class CastleCommandSpec extends WordSpec with Matchers{
  "A CastleCommand is used to purchase a Castle and" should{
    val controller = new Controller
    controller.createGrid("Test", "test")

    "place a Castle on a Field given by an Index" in{
      CastleCommand(6, controller).doStep()
      controller.grid(6).gamepiece.getClass should be(classOf[Castle])
    }

    "save the old GamePiece" in{
      val cc: CastleCommand = CastleCommand(5, controller)
      val gp = controller.grid(5).gamepiece
      cc.doStep()
      cc.memento should be (gp)
    }

    "undo and redo the Command" in{
      val cc: CastleCommand = CastleCommand(5, controller)
      val gp = controller.grid(5).gamepiece
      cc.doStep()
      cc.memento should be (gp)

      cc.undoStep()
      controller.grid(5).gamepiece should be (cc.memento)

      cc.redoStep()
      controller.grid(5).gamepiece.getClass should be(classOf[Castle])
    }
  }
}