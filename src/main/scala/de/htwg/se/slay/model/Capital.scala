package de.htwg.se.slay.model

class Capital(val player: Player) extends GamePiece {
  val strength: Int = 1
  var balance: Int = 0
}
