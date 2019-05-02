package de.htwg.se.slay.controller

import de.htwg.se.slay.model.{Grid, MapReader, Player}
import de.htwg.se.slay.util.Observable

class Controller extends Observable{
  private var players = Vector(Player("Player0", "\033[104m"))
  private var grid: Grid = _

  def gridToString: String = grid.toString

  def addPlayer(player: Player): Unit = players = players :+ player

  def createGrid(): Unit = {
    grid = new MapReader(players).gridCreator("Map1")
//    for(field <- grid(12).territory.fields){
//      field.owner = Player("asdasd", "\033[105m")
//    }
//    grid(12).territory.capital.owner = Player("asdasd", "\033[106m")
    notifyObservers()
  }
}
