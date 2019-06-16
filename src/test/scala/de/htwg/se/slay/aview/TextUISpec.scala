package de.htwg.se.slay.aview

import de.htwg.se.slay.controller._
import de.htwg.se.slay.model.Player
import org.scalatest._

class TextUISpec extends WordSpec with Matchers{
  "A TextUI handles the User Interface in the Console and" when{
    "new" should{
      val controller = new Controller
      val tui = new TextUI(controller)

      "be in the controllers subscribers list" in{
        controller.subscribers.contains(tui)
      }

      "print a Welcome Screen" in{
        //tui.welcomeScreen()
      }

      "prompt a players name" in{
        //tui.readPlayerName(1)
      }

      controller.addPlayer(Player("1",""))
      controller.addPlayer(Player("2",""))
      controller.createGrid("Test", "test")
      "update the grid or react to certain Events" in{
        tui.update(SuccessEvent())
        tui.update(MoneyErrorEvent())
        tui.update(new PlayerEvent(controller.players(1).toString))
        tui.update(new BalanceEvent(20))
        tui.update(OwnerErrorEvent())
        tui.update(GamePieceErrorEvent())
        tui.update(UnitErrorEvent())
        tui.update(UndoErrorEvent())
        tui.update(RedoErrorEvent())
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
        tui.processInput("mov C1 C3")
        tui.processInput("cmb C1 B1")
        tui.processInput("asdasd")
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
