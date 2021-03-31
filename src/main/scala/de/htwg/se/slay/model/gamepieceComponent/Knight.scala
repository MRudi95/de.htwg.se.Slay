package de.htwg.se.slay.model.gamepieceComponent

import de.htwg.se.slay.model.playerComponent.Player

case class Knight(player: Player,
                 hasMoved: Boolean = false,
                 strength: Int = 3,
                 price: Int = 30,
                 cost: Int = 18
                ) extends UnitGamePiece {
  override def copyTo(moved: Boolean) ={
    this.copy(hasMoved = moved)
  }
  override def toString: String = "3"
}