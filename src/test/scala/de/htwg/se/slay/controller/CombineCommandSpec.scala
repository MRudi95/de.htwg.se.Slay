package de.htwg.se.slay.controller

import de.htwg.se.slay.model._
import org.scalatest.{Matchers, WordSpec}

class CombineCommandSpec extends WordSpec with Matchers{
  "A CombineCommand is used to combine two UnitGamePieces and" should{
    val controller = new Controller
    controller.createGrid("Test", "test")

    "save the old GamePieces" in{
      val cmb: CombineCommand = new CombineCommand(4, 5, controller)
      val gp1 = controller.grid(4).gamepiece
      val gp2 = controller.grid(5).gamepiece

      cmb.memento1 should be (gp1)
      cmb.memento2 should be (gp2)
    }

    "combine two Peasants to a Spearman" in{
      val noPiece = NoPiece()
      controller.grid(4).gamepiece = new Peasant(controller.grid(4).owner)
      controller.grid(5).gamepiece = new Peasant(controller.grid(5).owner)

      new CombineCommand(4, 5, controller).doStep()
      controller.grid(4).gamepiece.getClass should be (classOf[Spearman])
      controller.grid(5).gamepiece should be (noPiece)
    }
    "combine two Spearmen to a Baron" in{
      val noPiece = NoPiece()
      controller.grid(4).gamepiece = new Spearman(controller.grid(4).owner)
      controller.grid(5).gamepiece = new Spearman(controller.grid(5).owner)

      new CombineCommand(4, 5, controller).doStep()
      controller.grid(4).gamepiece.getClass should be (classOf[Baron])
      controller.grid(5).gamepiece should be (noPiece)
    }
    "combine a Pesant and a Spearman to a Knight" in{
      val noPiece = NoPiece()
      controller.grid(4).gamepiece = new Peasant(controller.grid(4).owner)
      controller.grid(5).gamepiece = new Spearman(controller.grid(5).owner)

      new CombineCommand(4, 5, controller).doStep()
      controller.grid(4).gamepiece.getClass should be (classOf[Knight])
      controller.grid(5).gamepiece should be (noPiece)
    }
    "combine a Peasant and a Knight to a Baron" in{
      val noPiece = NoPiece()
      controller.grid(4).gamepiece = new Peasant(controller.grid(4).owner)
      controller.grid(5).gamepiece = new Knight(controller.grid(5).owner)

      new CombineCommand(4, 5, controller).doStep()
      controller.grid(4).gamepiece.getClass should be (classOf[Baron])
      controller.grid(5).gamepiece should be (noPiece)
    }

    "undo and redo the Command" in{
      val cmb: CombineCommand = new CombineCommand(4, 5, controller)
      controller.grid(4).gamepiece = new Peasant(controller.grid(4).owner)
      controller.grid(5).gamepiece = new Peasant(controller.grid(5).owner)
      cmb.doStep()

      cmb.undoStep()
      controller.grid(4).gamepiece.getClass should be (classOf[Peasant])
      controller.grid(5).gamepiece.getClass should be (classOf[Peasant])

      cmb.redoStep()
      controller.grid(4).gamepiece.getClass should be (classOf[Spearman])
      controller.grid(5).gamepiece.getClass should be(classOf[NoPiece])
    }
  }
}
