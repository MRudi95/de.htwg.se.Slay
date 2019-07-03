package de.htwg.se.slay.model.gridComponent

import de.htwg.se.slay.model.playerComponent.Player
import de.htwg.se.slay.model.gamepieceComponent.{GamePiece, NoPiece}

class Field(private var _owner: Player, private var _gamepiece: GamePiece = NoPiece()) {
  private var _neighbors: Neighbors = _
  private var _territory: Territory = _

  def owner:Player = _owner
  def owner_=(owner: Player):Unit = _owner = owner

  def gamepiece:GamePiece = _gamepiece
  def gamepiece_=(gamepiece: GamePiece):Unit = _gamepiece = gamepiece

  def neighbors:Neighbors = _neighbors
  def setNeighbors(neighbors: Neighbors):Boolean = {
    if(_neighbors == null) {_neighbors = neighbors; true} else false
  }

  def territory:Territory = _territory
  def territory_=(territory: Territory):Unit = _territory = territory

}
