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
    println("========== WELCOME TO SLAY ==========")
    println("\t Do You want to play a Game?\n")
    println("\t\t  \033[1;97m\033[42m YES \033[0m\t\t\033[1;97m\033[41m no \033[0m")
    val start = readLine()
    if(!tui.processStartup(start)){
      println("Ok, bye!")
      System.exit(0)
    }

    println("\nPlayer 1 enter your name:")
    val pOne = readLine()
    println("\nPlayer 2 enter your name:")
    val pTwo = readLine()
    println()

    val playerOne = Player(pOne, "\033[103m")
    val playerTwo = Player(pTwo, "\033[102m")

    val players = Vector(playerZero, playerOne, playerTwo)
    val grid:Grid = new MapReader(players).readMap("Map1")
    var input: String = ""
    do{
      tui.printGrid(grid)
      input = readLine()
    }while(input != "q" && input != "quit")
  }
}