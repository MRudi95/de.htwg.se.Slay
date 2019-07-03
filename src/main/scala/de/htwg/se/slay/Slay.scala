package de.htwg.se.slay

import de.htwg.se.slay.aview.{SwingGUI, TextUI}
import de.htwg.se.slay.controller.controllerComponent.controllerBaseImpl.Controller

import scala.io.StdIn.readLine

object Slay{
  val controller = new Controller
  val gui = new SwingGUI(controller)
  val tui = new TextUI(controller)

  def main(args: Array[String]) : Unit = {
    //StateStartUp.handle(WelcomeScreen(), controller)

    controller.createGrid("Map1")
    var input: String = ""
    controller.nextturn()
    do{
      input = readLine()
      tui.processInput(input)
    }while(input != "q" && input != "quit")
    System.exit(0)
  }
}