package de.htwg.se.slay.aview

import de.htwg.se.slay.controller.controllerComponent._
import de.htwg.se.slay.controller.controllerComponent.controllerBaseImpl.Controller
import org.scalatest._

class TextUISpec extends WordSpec with Matchers{
  "A TextUI handles the User Interface in the Console and" when{
    "new" should{
      val controller = new Controller
      val tui = new TextUI(controller)

      "be in the controllers subscribers list" in{
        controller.subscribers.contains(tui)
      }

      controller.createGrid("Test", "test")
      "update the grid or react to certain Events" in{
        tui.update(SuccessEvent())
        tui.update(MoneyErrorEvent())
        tui.update(PlayerEvent(controller.players(1).toString))
        tui.update(BalanceEvent(20, 20, 20))
        tui.update(OwnerErrorEvent())
        tui.update(GamePieceErrorEvent())
        tui.update(CombineErrorEvent())
        tui.update(UndoErrorEvent())
        tui.update(RedoErrorEvent())
        tui.update(MoveErrorEvent())
        tui.update(MovableErrorEvent())
        tui.update(MovedErrorEvent())
        tui.update(WelcomeEvent()) //do nothing
      }

      "process inputs" in{
        tui.processInput("q")
        tui.processInput("quit")
        tui.processInput("undo")
        tui.processInput("redo")
        tui.processInput("end")
        tui.processInput("ff20")
        tui.processInput("bal C1")
        tui.processInput("buy C1")
        tui.processInput("plc C1")
        tui.processInput("mov C1 C2")
        tui.processInput("cmb C1 B1")
        tui.processInput("asdasd")
        val controller2 = new Controller
        val tui2 = new TextUI(controller2)
        tui2.processInput("load test")
        tui2.processInput("save test")

      }


      "be able to check if an Index in the format 'A1' is valid to access the Grid" in{
        //still needs alot of other possibilities
        tui.checkIndex("A1") should be (true)
        tui.checkIndex("D2") should be (true)
        tui.checkIndex("A7") should be (false)
        tui.checkIndex("D9") should be (false)
      }

      "be able to convert an Index in the format 'A1' to an Integer Index" in{
        tui.convertIndex("A1") should be (0)
        tui.convertIndex("B1") should be (1)
        tui.convertIndex("C1") should be (2)
        tui.convertIndex("D4") should be (15)
      }

      "be able to convert an Index from Integer to the format 'A1'" in{
        tui.convertIndex(0) should be ("A1")
        tui.convertIndex(1) should be ("B1")
        tui.convertIndex(2) should be ("C1")
        tui.convertIndex(15) should be ("D4")
      }
    }
  }
}
