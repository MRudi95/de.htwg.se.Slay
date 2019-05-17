package de.htwg.se.slay.aview

import de.htwg.se.slay.controller.Controller
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
        tui.welcomeScreen()
      }

      "process the Welcome" in{
        tui.processWelcome("yes")
      }

      "prompt a players name" in{
        tui.readPlayerName(1)
      }
    }
  }
}
