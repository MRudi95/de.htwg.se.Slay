package de.htwg.se.slay.model

case class Castle(player: Player) extends GamePiece {
  val strength: Int = 2
  val price: Int = 15

  override def toString: String = "B"
}
