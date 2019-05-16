package de.htwg.se.slay.aview


import de.htwg.se.slay.controller.Controller
import de.htwg.se.slay.model._
import de.htwg.se.slay.util.Observer

class TextUI(controller: Controller) extends Observer{

  controller.add(this)

  private val R = "\033[0m"       //Color Reset
  private val B = "\033[1;97m"    //Text Color Black
  private val RED = "\033[41m"    //Red Background
  private val GREEN = "\033[42m"  //Green Background

  def printGrid(grid:Grid): Unit = println(grid.toString)

  def welcomeScreen(): Unit = {
    println("\n========== WELCOME TO SLAY ==========\n" +
            "\t Do You want to play a Game?\n\n" +
            "\t\t  " + B + GREEN + " YES " + R + "\t\t" + B + RED + " no " + R)
  }

  def processWelcome(input: String): Unit = {
    if(input.toUpperCase != "YES"){
      println("Ok, bye!")
      System.exit(0)
    }
  }

  def readPlayerName(player: Int): Unit = println("\n Player " + player + " enter your name:")

  def processInput(input: String): Unit = {
    val regexIndex = "bal [A-Z]\\d+".r
    input match {
      case "q" =>
      case "quit" =>
      case "money" => controller.moneymoney()
      case regexIndex(_*) if controller.checkIndex(input.replaceAll("bal ", "")) && controller.grid(controller.convertIndex(input.replaceAll("bal ", ""))).gamepiece.isInstanceOf[Capital] =>
        println(controller.grid(controller.convertIndex(input.replaceAll("bal ", ""))).gamepiece.asInstanceOf[Capital].balance)
      case "test" => controller.testStuff()
      case _ => println("Wrong Input!")
    }
  }

  override def update(): Unit = println(controller.gridToString)
}

