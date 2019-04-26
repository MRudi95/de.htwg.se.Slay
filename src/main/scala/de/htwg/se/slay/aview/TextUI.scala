package de.htwg.se.slay.aview

import de.htwg.se.slay.model._

class TextUI {
  private val R = "\033[0m"       //Color Reset
  private val B = "\033[1;97m"    //Text Color Black
  private val U = "\033[4;97m"    //Underline/Fieldseparator

  private var firstLine = "  "

  def printGrid(grid:Grid): Unit = println(buildGridString(grid))

  def welcomeScreen(): Unit = {
    println("\n========== WELCOME TO SLAY ==========\n" +
            "\t Do You want to play a Game?\n\n" +
            "\t\t  \033[1;97m\033[42m YES \033[0m\t\t\033[1;97m\033[41m no \033[0m")
  }

  def processStartup(input: String): Unit = {
    if(input != "YES"){
      println("Ok, bye!")
      System.exit(0)
    }
  }

  def readPlayerName(player: String): Unit = println("\n" + player + " enter your name:")

  private def buildGridString(grid:Grid): String ={
    var returnGrid = "\n"
    if(firstLine == "  "){
      var char:Int = 65 // char 'A'
      for(cols <- 0 to grid.colIdx){
        firstLine += "  " + char.toChar + "  "
        char += 1
      }
    }
    returnGrid += firstLine + "\n"

    var lineOne = ""
    var lineTwo = ""
    var lineThree = ""

    for(rows <- 0 to grid.rowIdx) {
      for (cols <- 0 to grid.colIdx) {
        val idx = rows * (grid.colIdx+1) + cols
        val gamePiece = getGamePiece(grid(idx))
        lineOne   += grid(idx).owner.color + "     "
        lineTwo   += grid(idx).owner.color + "  " + grid(idx).owner.color + B + gamePiece + "  "
        lineThree += grid(idx).owner.color + "     "
      }
      returnGrid += "  " + lineOne + R + "\n"
      returnGrid += rows+1 + " " + lineTwo + R + "\n"
      returnGrid += "  " + lineThree + R + "\n"
      lineOne = ""
      lineTwo = ""
      lineThree = ""
    }
    returnGrid
  }

  private def getGamePiece(field:Field):String = {
    field.gamepiece match{
      case _:Tree     => "T"
      case _:Castle   => "B"
      case _:Capital  => "C"
      case _:Grave    => "G"
      case _:Peasant  => "1"
      case _:Spearman => "2"
      case _:Knight   => "3"
      case _:Baron    => "4"
      case null       => " "
    }
  }
}

