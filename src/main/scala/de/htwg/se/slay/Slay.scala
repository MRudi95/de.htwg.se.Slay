package de.htwg.se.slay

import de.htwg.se.slay.aview.TextUI
import de.htwg.se.slay.aview.gui.SwingGUI
import de.htwg.se.slay.controller._
import de.htwg.se.slay.model.Player

import scala.io.StdIn.readLine

object Slay{
  val controller = new Controller
  val gui = new SwingGUI(controller)
  val tui = new TextUI(controller)


  StateStartUp.handle(WelcomeScreen(), controller)

  def main(args: Array[String]) : Unit = {
    var input: String = ""
    controller.nextturn()
    do{
      input = readLine()
      tui.processInput(input)
    }while(input != "q" && input != "quit")
    System.exit(0)
  }
}