package de.htwg.se.slay.model.gridComponent

import de.htwg.se.slay.model.gamepieceComponent.GamePiece
import de.htwg.se.slay.model.playerComponent.Player

trait FieldInterface {
  def owner:Player
  def owner_=(owner: Player):Unit

  def gamepiece:GamePiece
  def gamepiece_=(gamepiece: GamePiece):Unit

  def neighbors:NeighborInterface
  def setNeighbors(neighbors: NeighborInterface):Boolean

  def territory: Option[TerritoryInterface]
  def territory_=(territory: Option[TerritoryInterface]):Unit
}

trait FieldFactory{
  def create(_owner: Player): FieldInterface
}
