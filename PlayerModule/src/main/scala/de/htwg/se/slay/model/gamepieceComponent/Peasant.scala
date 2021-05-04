package de.htwg.se.slay.model.gamepieceComponent

import de.htwg.se.slay.model.playerComponent.Player

case class Peasant(player: Player,
                 hasMoved: Boolean = false,
                 strength: Int = 1,
                 price: Int = 10,
                 cost: Int = 2
                ) extends UnitGamePiece {
  override def copyTo(moved: Boolean) ={
    this.copy(hasMoved = moved)
  }
  override def toString: String = "1"
}
