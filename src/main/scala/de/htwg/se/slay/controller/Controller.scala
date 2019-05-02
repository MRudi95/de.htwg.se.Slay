package de.htwg.se.slay.controller

import de.htwg.se.slay.model._
import de.htwg.se.slay.util.Observable

import scala.collection.immutable.HashSet

class Controller extends Observable{
  private var players = Vector(Player("Player0", "\033[104m"))
  private var grid: Grid = _
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


  def testStuff(): Unit ={

    for(field <- grid(14).territory.fields){
      field.owner = Player("asdasd", "\033[105m")
    }
    grid(14).territory.capital.owner = Player("asdasd", "\033[106m")
    for(field <- capitals){
      val cap = field.gamepiece.asInstanceOf[Capital]
      cap.balance += field.territory.length()
      //println(cap.balance)
    }


    notifyObservers()
  }
}
