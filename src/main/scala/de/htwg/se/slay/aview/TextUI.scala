package de.htwg.se.slay.aview


import de.htwg.se.slay.controller.{Controller, Event, MoneyError, Success}
import de.htwg.se.slay.model._
import de.htwg.se.slay.util.Observer

class TextUI(controller: Controller) extends Observer{

  controller.add(this)

  private val R = "\033[0m"       //Color Reset
  private val B = "\033[1;97m"    //Text Color Black
  private val RED = "\033[41m"    //Red Background
  private val GREEN = "\033[42m"  //Green Background

  def welcomeScreen(): Unit = {
    println("\n========== WELCOME TO SLAY ==========\n" +
            "\t Do You want to play a Game?\n\n" +
            "\t\t  " + B + GREEN + " YES " + R + "\t\t" + B + RED + " no " + R)
  }

  def processWelcome(input: String): Boolean = {
    if(input.toUpperCase != "YES"){
      println("Ok, bye!")
      true
    } else false
  }

  def readPlayerName(player: Int): Unit = println("\n Player " + player + " enter your name:")

  def processInput(input: String): Unit = {
    val coord = "[A-Z]\\d+".r
    val bal = s"bal ($coord)".r
    val buy = s"buy ($coord)".r
    val plc = s"plc ($coord)".r
    val mov = s"mov ($coord) ($coord)".r
    val cmb = s"cmb ($coord) ($coord)".r

    input match {
      case "q" =>
      case "quit" =>
      case "undo" =>
      case "redo" =>
      case "end" => controller.moneymoney()
      case bal(c) if checkIndex(c) =>
        println("balance: " + controller.grid(convertIndex(c)).territory.capital.balance)
      case buy(c) if checkIndex(c) && checkNoPiece(convertIndex(c)) =>
        controller.buyPeasant(convertIndex(c))
      case plc(c) if checkIndex(c) && checkNoPiece(convertIndex(c)) =>
        controller.placeCastle(convertIndex(c))
      case mov(c1, c2) if checkIndex(c1) && checkIndex(c2) =>
      case cmb(c1, c2) if checkIndex(c1) && checkIndex(c2) =>
      case _ => println("Wrong Input!")
    }
  }

  def checkNoPiece(idx: Int): Boolean ={
    controller.grid(idx).gamepiece.isInstanceOf[NoPiece] || controller.grid(idx).gamepiece.isInstanceOf[Tree]
  }

  def checkIndex(idx: String): Boolean ={
    //need better option for cols extraction
    val coord = "([A-Z])(\\d+)".r
    val coord(cols, rows) = idx
    val col = cols.charAt(0).toInt - 65
    if(col > controller.grid.colIdx || rows.toInt - 1 > controller.grid.rowIdx) false else true
  }

  def convertIndex(idx: Int): String ={
    val cols = idx % (controller.grid.colIdx+1) + 65
    val rows = idx / (controller.grid.colIdx+1) + 1
    cols.toChar + rows.toString
  }

  def convertIndex(idx: String): Int ={
    val cols = idx.charAt(0).toInt - 65
    val rows = idx.charAt(1).asDigit - 1
    rows * (controller.grid.colIdx+1) + cols
  }

  override def update(e: Event): Boolean = {
    e match{
      case _:Success =>
        println(controller.gridToString); true
      case _:MoneyError =>
        println("Not enough Money!"); true
    }


  }
}

