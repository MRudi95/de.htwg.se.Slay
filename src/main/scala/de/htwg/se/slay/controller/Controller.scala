package de.htwg.se.slay.controller

import de.htwg.se.slay.model._
import de.htwg.se.slay.util.Observable

import scala.collection.immutable.HashSet

class Controller extends Observable{
  private var players = Vector(Player("Player0", "\033[104m"))
  var grid: Grid = _
  private var capitals: HashSet[Field] = _

  def gridToString: String = grid.toString

  def addPlayer(player: Player): Unit = players = players :+ player

  def createGrid(): Unit = {
    val (g, c) = new MapReader(players).gridCreator("Map1")
    grid = g
    capitals = c
    notifyObservers()
  }

  def checkIndex(idx: String): Boolean ={
    val cols = idx.charAt(0).toInt - 65
    val rows = idx.charAt(1).asDigit - 1
    if(cols > grid.colIdx || rows > grid.rowIdx) false else true
  }

  def convertIndex(idx: Int): String ={
    val cols = idx % (grid.colIdx+1) + 65
    val rows = idx / (grid.colIdx+1) + 1
    cols.toChar + rows.toString
  }

  def convertIndex(idx: String): Int ={
    val cols = idx.charAt(0).toInt - 65
    val rows = idx.charAt(1).asDigit - 1
    cols + rows
  }

  def moneymoney(): Unit = {
    for(field <- capitals){
      val cap = field.gamepiece.asInstanceOf[Capital]
      cap.balance += field.territory.length()
    }
  }

  def testStuff(): Unit ={

    for(field <- grid(104).territory.fields){
      field.owner = Player("asdasd", "\033[105m")
    }
    grid(14).territory.capital.owner = Player("asdasd", "\033[106m")
    for(field <- capitals){
      val cap = field.gamepiece.asInstanceOf[Capital]
      cap.balance += field.territory.length()
      println(convertIndex(cap.idx) + ":\t" + cap.balance)
    }
//    for(field <- grid(100).neighbors){
//      if(field != null) field.owner = Player("asdasd", "\033[106m")
//    }


    notifyObservers()
  }
}
