package de.htwg.se.slay.model

class Field(private var _owner: Player) {
  private var _gamepiece: GamePiece = _
  private var _neighbors: Neighbors= _

  def owner:Player = _owner
  def owner_=(owner: Player):Unit = _owner = owner

  def gamepiece:GamePiece = _gamepiece
  def gamepiece_=(gamepiece: GamePiece):Unit = _gamepiece = gamepiece

  def neighbors:Neighbors = _neighbors
  def setNeighbors(neighbors: Neighbors):Boolean = {
    if(_neighbors == null) {_neighbors = neighbors; true} else false
  }
}
