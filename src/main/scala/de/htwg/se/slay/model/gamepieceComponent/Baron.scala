package de.htwg.se.slay.model.gamepieceComponent

import de.htwg.se.slay.model.playerComponent.Player

case class Baron(player: Player,
            hasMoved: Boolean = false,
            strength: Int = 4,
            price: Int = 40,
            cost: Int = 54
           ) extends UnitGamePiece {
  override def copyTo(moved: Boolean) ={
    this.copy(hasMoved = moved)
  }
  override def toString: String = "4"
}
