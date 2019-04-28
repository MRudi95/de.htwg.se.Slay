package de.htwg.se.slay.model

case class NoPiece() extends GamePiece {
  override val player: Player = null
  override val strength: Int = 0

  override def toString: String = " "
}
