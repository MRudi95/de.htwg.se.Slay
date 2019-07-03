package de.htwg.se.slay.model.gamepieceComponent

import de.htwg.se.slay.model.playerComponent.Player

case class Castle(player: Player) extends GamePiece {
  val strength: Int = 2
  val price: Int = 15

  override def toString: String = "B"
}
