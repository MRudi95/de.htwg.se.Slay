package de.htwg.se.slay

import de.htwg.se.slay.aview.TextUI
import de.htwg.se.slay.aview.gui.SwingGUI
import de.htwg.se.slay.controller.Controller
import de.htwg.se.slay.model.Player

import scala.io.StdIn.readLine

object Slay{
  val controller = new Controller
  val tui = new TextUI(controller)

  tui.welcomeScreen()
  if(tui.processWelcome(readLine())) System.exit(0)

  tui.readPlayerName(1)
  controller.addPlayer(Player(readLine(), "\033[103m"))
  tui.readPlayerName(2)
  controller.addPlayer(Player(readLine(), "\033[102m"))

  controller.createGrid("Map1")

  val gui = new SwingGUI(controller)

  def main(args: Array[String]) : Unit = {
    var input: String = ""
    controller.nextturn()
    do{
      input = readLine()
      tui.processInput(input)
    }while(input != "q" && input != "quit")
  }
}