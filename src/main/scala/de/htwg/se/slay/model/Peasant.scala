package de.htwg.se.slay.model

class Peasant(val player: Player) extends UnitGamePiece {
  override val strength: Int = 1
  override val price: Int = 10
  override val cost: Int = 2
  var hasMoved: Boolean = false

  override def toString: String = "1"
}
