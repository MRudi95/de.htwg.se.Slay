package de.htwg.se.slay

import de.htwg.se.slay.aview.TextUI
import de.htwg.se.slay.model.{Grid, MapReader, Player}

object Slay{
  val players = Vector(Player("Player0", "\033[104m"), Player("Player1", "\033[103m"), Player("Player2", "\033[42m"))
  val grid:Grid = new MapReader(players).readMap("Map1")
  val tui = new TextUI

  def main(args: Array[String]) : Unit = {
    val testplayer = Player("", "\033[105m")
    val testfield = 20
    for(f <- grid(testfield).neighbors) if(f != null) f.owner_=(testplayer)
    tui.printGrid(grid)
  }
}