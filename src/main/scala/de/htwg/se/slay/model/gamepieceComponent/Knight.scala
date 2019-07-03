package de.htwg.se.slay.model.gamepieceComponent

import de.htwg.se.slay.model.playerComponent.Player

class Knight(val player: Player) extends UnitGamePiece {
  override val strength: Int = 3
  override val price: Int = 30
  override val cost: Int = 18
  var hasMoved: Boolean = false

  override def toString: String = "3"
}
