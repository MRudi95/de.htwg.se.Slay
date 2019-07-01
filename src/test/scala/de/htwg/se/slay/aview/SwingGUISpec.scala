package de.htwg.se.slay.aview

import de.htwg.se.slay.aview.gui.SwingGUI
import de.htwg.se.slay.controller._
import org.scalatest._

class SwingGUISpec extends WordSpec with Matchers{
  "A SwingGUI handles the Graphical User Interface and" when{
    "new" should{
      val controller = new Controller
      val gui = new SwingGUI(controller)
      controller.createGrid("Test", "test")
    }
  }
}