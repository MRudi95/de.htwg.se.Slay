package de.htwg.se.slay

import de.htwg.se.slay.aview.TextUI
import de.htwg.se.slay.controller.Controller
import de.htwg.se.slay.model.{Grid, MapReader, Player}

import scala.io.StdIn.readLine

object Slay{
  val controller = new Controller
  val tui = new TextUI(controller)

  tui.welcomeScreen()
  tui.processWelcome(readLine())

  tui.readPlayerName(1)
  controller.addPlayer(Player(readLine(), "\033[103m"))
  tui.readPlayerName(2)
  controller.addPlayer(Player(readLine(), "\033[102m"))

  controller.createGrid()

  def main(args: Array[String]) : Unit = {
    var input: String = ""

    do{
      input = readLine()
    }while(input != "q" && input != "quit")
  }
}