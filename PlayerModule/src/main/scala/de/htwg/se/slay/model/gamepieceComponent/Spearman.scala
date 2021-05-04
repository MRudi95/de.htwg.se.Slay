package de.htwg.se.slay.model.gamepieceComponent

import de.htwg.se.slay.model.playerComponent.Player

case class Spearman(player: Player,
                 hasMoved: Boolean = false,
                 strength: Int = 2,
                 price: Int = 20,
                 cost: Int = 6
                ) extends UnitGamePiece {
  override def copyTo(moved: Boolean) ={
    this.copy(hasMoved = moved)
  }
  override def toString: String = "2"
}
