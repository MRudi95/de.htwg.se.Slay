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

  def buyPeasant(c: Int): Unit = {
    if (grid(c).territory.capital.balance >= 10) {
      grid(c).territory.capital.balance -= 10
      grid(c).gamepiece = new Peasant(grid(c).owner)
      notifyObservers()
    } else notifyObservers(new MoneyError)

  }

  def placeCastle(c: Int): Unit ={
    if (grid(c).territory.capital.balance >= 15) {
      grid(c).territory.capital.balance -= 15
      grid(c).gamepiece = Castle(grid(c).owner)
      notifyObservers()
    } else notifyObservers(new MoneyError)
  }

  def moneymoney(): Unit = {
    for(field <- capitals){
      val cap = field.gamepiece.asInstanceOf[Capital]
      cap.balance += field.territory.size()
    }
    notifyObservers()
  }

}
