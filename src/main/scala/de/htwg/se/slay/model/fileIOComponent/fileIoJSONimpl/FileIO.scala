package de.htwg.se.slay.model.fileIOComponent.fileIoJSONimpl

import de.htwg.se.slay.model.fileIOComponent.FileIOInterface
import de.htwg.se.slay.model.gridComponent.GridInterface
import de.htwg.se.slay.model.playerComponent.Player

class FileIO extends FileIOInterface{
  //override def load(name: String, players: Vector[Player]): (String, String, Int, GridInterface) = ???

  override def save(name: String, players: Vector[Player], state: Int, grid: GridInterface): Unit = ???
}
