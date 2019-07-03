package de.htwg.se.slay.model.fileIOComponent

import de.htwg.se.slay.model.gridComponent.GridInterface
import de.htwg.se.slay.model.playerComponent.Player

trait FileIOInterface {
  def load(name: String, players: Vector[Player]): (String, String, Int, GridInterface)
  def save(name: String, players: Vector[Player], state: Int, grid: GridInterface): Unit
}
