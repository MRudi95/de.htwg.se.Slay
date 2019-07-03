package de.htwg.se.slay.model.gamepieceComponent

import de.htwg.se.slay.model.playerComponent.Player

trait GamePiece {
  val player: Player
  val strength: Int
}
