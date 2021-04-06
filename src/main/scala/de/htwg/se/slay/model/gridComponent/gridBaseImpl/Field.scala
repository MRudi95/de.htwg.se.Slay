package de.htwg.se.slay.model.gridComponent.gridBaseImpl

import com.google.inject.Inject
import com.google.inject.assistedinject.Assisted
import de.htwg.se.slay.model.gamepieceComponent.{GamePiece, NoPiece}
import de.htwg.se.slay.model.gridComponent.{FieldInterface, NeighborInterface, TerritoryInterface}
import de.htwg.se.slay.model.playerComponent.Player

class Field @Inject() (@Assisted private var _owner: Player) extends FieldInterface {

  private var _gamepiece: GamePiece = NoPiece()
  private var _neighbors: NeighborInterface = _
  private var _territory: Option[TerritoryInterface] = None

  def owner:Player = _owner
  def owner_=(owner: Player):Unit = _owner = owner

  def gamepiece: GamePiece = _gamepiece
  def gamepiece_=(gamepiece: GamePiece):Unit = _gamepiece = gamepiece

  def neighbors:NeighborInterface = _neighbors
  def setNeighbors(neighbors: NeighborInterface):Boolean = {
    if(_neighbors == null) {_neighbors = neighbors; true} else false
  }

  def territory: Option[TerritoryInterface] = _territory
  def territory_=(territory: Option[TerritoryInterface]):Unit = _territory = territory

}
