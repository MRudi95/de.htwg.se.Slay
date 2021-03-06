package de.htwg.se.slay.util

import de.htwg.se.slay.controller.controllerComponent.controllerBaseImpl.{BuyCommand, Controller}
import de.htwg.se.slay.model.playerComponent.Player
import org.scalatest.{Matchers, WordSpec}

class UndoManagerSpec extends WordSpec with Matchers{
  "An UndoManager is responsible for undo/redo operations and" should{
    val controller = new Controller
    val playr1 = new Player("1","")
    val playr2 = new Player("2","")
    controller.addPlayer(playr1)
    controller.addPlayer(playr2)
    controller.createGrid("Test", "test")

    "reset its undo- and redoStack" in{
      val undoManager = new UndoManager
      undoManager.reset()
    }
    "add a Command to its undoStack and  execute that Command" in{
      val undoManager = new UndoManager
      undoManager.doStep(BuyCommand(6, controller))
    }
    "undo the top Command from its undoStack and move it to the redoStack" in{
      val redo = new UndoManager
      redo.doStep(BuyCommand(6, controller))
      redo.undoStep() should be (true)
    }
    "redo the top Command from its redoStack and move it to the undoStack" in{
      val undo = new UndoManager
      undo.doStep(BuyCommand(6, controller))
      undo.undoStep()
      undo.redoStep() should be (true)
    }
    "return false when trying to undo or redo with no Commands on the corresponding Stacks" in{
      val manager = new UndoManager
      manager.undoStep() should be(false)
      manager.redoStep() should be(false)
    }
  }
}
