package de.htwg.se.slay

import de.htwg.se.slay.aview.TextUI
import de.htwg.se.slay.model.{Grid, MapReader, Player}

import scala.io.StdIn.readLine

object Slay{
  val playerZero = Player("Player0", "\033[104m")
  val tui = new TextUI

  def main(args: Array[String]) : Unit = {
    //val testplayer = Player("", "\033[105m")
    //val testfield = 20
    //for(f <- grid(testfield).neighbors) if(f != null) f.owner_=(testplayer)
    tui.welcomeScreen()
    val start = readLine()
    tui.processStartup(start)

    tui.readPlayerName("Player 1")
    val playerOne = Player(readLine(), "\033[103m")
    tui.readPlayerName("Player 2")
    val playerTwo = Player(readLine(), "\033[102m")

    val players = Vector(playerZero, playerOne, playerTwo)
    val grid:Grid = new MapReader(players).readMap("Map1")

    var input: String = ""
    do{
      tui.printGrid(grid)
      input = readLine()
    }while(input != "q" && input != "quit")
  }
}