package de.htwg.se.slay.model

class Field(private var _owner: Player) {
  private var _gamepiece: GamePiece = _
  private var _neighbors: Vector[Field] = _

  def owner:Player = _owner
  def owner_=(owner: Player):Unit = _owner = owner

  def gamepiece:GamePiece = _gamepiece
  def gamepiece_=(gamepiece: GamePiece):Unit = _gamepiece = gamepiece

  def neighbors:Vector[Field] = _neighbors
  def setNeighbors(neighbors: Vector[Field]):Boolean = {
    if(neighbors.length != 4 || _neighbors != null) return false
    _neighbors = neighbors
    true
  }
}
