package de.htwg.se.slay.model

class Spearman(val player: Player) extends UnitGamePiece {
  override val strength: Int = 2
  override val price: Int = 20
  override val cost: Int = 6
  override var hasMoved: Boolean = false
}
