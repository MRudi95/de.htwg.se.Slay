package de.htwg.se.slay.aview

import de.htwg.se.slay.controller._
import de.htwg.se.slay.model.Player
import de.htwg.se.slay.util.Observer

import scala.io.StdIn.readLine


class TextUI(controller: Controller) extends Observer{

  controller.add(this)

  private val R = "\033[0m"         //Color Reset
  private val B = "\033[1;97m"      //Text Color Black
  private val RED = "\033[41m"      //Red Background
  private val GREEN = "\033[42m"    //Green Background
  private val P1COLOR = "\033[103m" //Yellow
  private val P2COLOR = "\033[102m" //Green

  def welcomeScreen(): Unit = {
    println("\n========== WELCOME TO SLAY ==========\n" +
            "\t Do You want to play a Game?\n\n" +
            "\t\t  " + B + GREEN + " YES " + R + "\t\t" + B + RED + " no " + R)

    readLine().toUpperCase match {
      case "YES" =>
        StateStartUp.handle(ReadPlayerName(1), controller)
      case "" =>
      case _ =>
        println("Ok, bye!")
        System.exit(0)
    }
  }

  def readPlayerName(player: Int): Unit= {
    println("\n Player " + player + " enter your name:")

    notify() //dat exception though, but need that context switch

    val name = readLine()
    if(name != "") {
      player match {
        case 1 =>
          controller.addPlayer(Player(name, P1COLOR))
        case 2 =>
          controller.addPlayer(Player(name, P2COLOR))
        case _ =>
      }
    }
    StateStartUp.handle(ReadPlayerName(player+1), controller)
  }



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
      case "undo" => controller.undo()
      case "redo" => controller.redo()
      case "end" => controller.nextturn()
      case "ff20" => controller.surrender()
      case bal(c) if checkIndex(c) =>
        controller.seeBalance(convertIndex(c))
      case buy(c) if checkIndex(c) =>
        controller.buyPeasant(convertIndex(c))
      case plc(c) if checkIndex(c) =>
        controller.placeCastle(convertIndex(c))
      case mov(c1, c2) if checkIndex(c1) && checkIndex(c2) =>
        controller.moveUnit(convertIndex(c1), convertIndex(c2))
      case cmb(c1, c2) if checkIndex(c1) && checkIndex(c2) =>
        controller.combineUnit(convertIndex(c1), convertIndex(c2))
      case _ => println("Wrong Input!")
    }
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
      case _: SuccessEvent =>
        println(controller.gridToString); true
      case _: GridCreatedEvent =>
        println(controller.gridToString); true
      case _: WelcomeEvent =>
        welcomeScreen(); true
      case r: ReadPlayerEvent =>
        readPlayerName(r.player); true
      case _: MoneyErrorEvent =>
        println("Not enough Money!"); true
      case p: PlayerEvent =>
        println("It is your turn " + p.name.toUpperCase + " !"); true
      case b: BalanceEvent =>
        println("balance: " + b.bal); true
      case _: OwnerErrorEvent =>
        println("You are not the Owner of this!"); true
      case _: GamePieceErrorEvent =>
        println("There already is a GamePiece there!"); true
      case _: UnitErrorEvent =>
        println("Can't combine those Units!"); true
      case _: UndoErrorEvent =>
        println("Nothing to undo!"); true
      case _: RedoErrorEvent =>
        println("Nothing to redo!"); true
      case v: VictoryEvent =>
        println(v.name.toUpperCase + " has won the Game!"); true
      case _ => false
    }
  }
}

