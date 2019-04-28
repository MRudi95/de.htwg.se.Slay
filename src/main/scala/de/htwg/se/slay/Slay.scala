package de.htwg.se.slay

import de.htwg.se.slay.aview.TextUI
import de.htwg.se.slay.model.{Grid, MapReader, Player}

import scala.io.StdIn.readLine

object Slay{
  val playerZero = Player("Player0", "\033[104m")
  val tui = new TextUI

  def main(args: Array[String]) : Unit = {
    tui.welcomeScreen()
    tui.processWelcome(readLine())

    tui.readPlayerName(1)
    val playerOne = Player(readLine(), "\033[103m")
    tui.readPlayerName(2)
    val playerTwo = Player(readLine(), "\033[102m")

    val players = Vector(playerZero, playerOne, playerTwo)
    val grid:Grid = MapReader.readMap("Map1", players)

    var input: String = ""
    do{
      tui.printGrid(grid)
      input = readLine()
    }while(input != "q" && input != "quit")
  }
}