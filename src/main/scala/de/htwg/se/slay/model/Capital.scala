package de.htwg.se.slay.model

class Capital(val player: Player, val idx: Int) extends GamePiece {
  val strength: Int = 1
  var balance: Int = 10

  override def toString: String = "C"
}
