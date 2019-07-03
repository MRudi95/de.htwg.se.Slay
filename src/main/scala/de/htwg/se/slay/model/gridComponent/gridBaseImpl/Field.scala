package de.htwg.se.slay.model.gridComponent.gridBaseImpl

import de.htwg.se.slay.model.gamepieceComponent.{GamePiece, NoPiece}
import de.htwg.se.slay.model.gridComponent.{FieldInterface, NeighborInterface, TerritoryInterface}
import de.htwg.se.slay.model.playerComponent.Player

class Field(private var _owner: Player, private var _gamepiece: GamePiece = NoPiece()) extends FieldInterface {
  private var _neighbors: NeighborInterface = _
  private var _territory: TerritoryInterface = _

  def owner:Player = _owner
  def owner_=(owner: Player):Unit = _owner = owner

  def gamepiece: GamePiece = _gamepiece
  def gamepiece_=(gamepiece: GamePiece):Unit = _gamepiece = gamepiece

  def neighbors:NeighborInterface = _neighbors
  def setNeighbors(neighbors: NeighborInterface):Boolean = {
    if(_neighbors == null) {_neighbors = neighbors; true} else false
  }

  def territory: TerritoryInterface = _territory
  def territory_=(territory: TerritoryInterface):Unit = _territory = territory

}
