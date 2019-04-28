package de.htwg.se.slay.model

class Baron(val player: Player) extends UnitGamePiece {
  override val strength: Int = 4
  override val price: Int = 40
  override val cost: Int = 54
  var hasMoved: Boolean = false

  override def toString: String = "4"
}
