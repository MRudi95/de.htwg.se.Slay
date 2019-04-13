package de.htwg.se.slay.model

case class Tower(player: Player) extends GamePiece {
  val strength: Int = 2
  val price: Int = 15
}
