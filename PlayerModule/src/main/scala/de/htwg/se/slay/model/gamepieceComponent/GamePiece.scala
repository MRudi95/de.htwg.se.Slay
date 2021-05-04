package de.htwg.se.slay.model.gamepieceComponent

import de.htwg.se.slay.model.playerComponent.Player

trait GamePiece {
  def player: Player
  val strength: Int
}
