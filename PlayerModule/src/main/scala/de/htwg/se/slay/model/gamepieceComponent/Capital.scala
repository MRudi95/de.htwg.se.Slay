package de.htwg.se.slay.model.gamepieceComponent

import de.htwg.se.slay.model.playerComponent.Player

class Capital(val player: Player) extends GamePiece {
  val strength: Int = 1
  var balance: Int = 10

  override def toString: String = "C"
}
