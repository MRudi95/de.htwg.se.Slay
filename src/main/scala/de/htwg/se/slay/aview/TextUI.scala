package de.htwg.se.slay.aview

import de.htwg.se.slay.model._

class TextUI {
  private val R = "\033[0m"       //Color Reset
  private val B = "\033[1;97m"    //Text Color Black
  private val U = "\033[4;97m"    //Underline/Fieldseparator

  private var firstLine = "  "

  def printGrid(grid:Grid): Unit ={
    if(firstLine == "  "){
      var char:Int = 65 // char 'A'
      for(cols <- 0 to grid.colIdx){
        firstLine += "  " + char.toChar + "  "
        char += 1
      }
    }
    println(firstLine)

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
      println("  " + lineOne + R)
      println(rows+1 + " " + lineTwo + R)
      println("  " + lineThree + R)
      lineOne = ""
      lineTwo = ""
      lineThree = ""
    }
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

  def processStartup(input: String): Boolean = {
    if(input == "YES") true else false
  }
}

