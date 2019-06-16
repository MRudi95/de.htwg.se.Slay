package de.htwg.se.slay

import de.htwg.se.slay.aview.TextUI
import de.htwg.se.slay.aview.gui.SwingGUI
import de.htwg.se.slay.controller._

import scala.io.StdIn.readLine

object Slay{
  val controller = new Controller
  val gui = new SwingGUI(controller)
  val tui = new TextUI(controller)

  def main(args: Array[String]) : Unit = {
    StateStartUp.handle(WelcomeScreen(), controller)

    var input: String = ""
    controller.nextturn()
    do{
      input = readLine()
      tui.processInput(input)
    }while(input != "q" && input != "quit")
    System.exit(0)
  }
}