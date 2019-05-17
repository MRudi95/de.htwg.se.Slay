package de.htwg.se.slay.controller

import de.htwg.se.slay.model._
import de.htwg.se.slay.util.Observable

import scala.collection.immutable.HashSet

class Controller extends Observable{
  var players: Vector[Player] = Vector(Player("Player0", "\033[104m"))
  var grid: Grid = _
  var capitals: HashSet[Field] = _

  def gridToString: String = grid.toString

  def addPlayer(player: Player): Unit = players = players :+ player

  def createGrid(mapname: String, typ: String = "main"): Unit = {
    val (g, c) = new MapReader(players).gridCreator(mapname, typ)
    grid = g
    capitals = c
    notifyObservers()
  }

  def checkIndex(idx: String): Boolean ={
    //still needs alot of other possibilities
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
    rows * (cols+1) + cols
  }

  def moneymoney(): Unit = {
    for(field <- capitals){
      val cap = field.gamepiece.asInstanceOf[Capital]
      cap.balance += field.territory.size()
    }
  }

}
