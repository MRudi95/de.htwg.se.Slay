package de.htwg.se.slay.model.gamepieceComponent

trait UnitGamePiece extends GamePiece {
  val hasMoved: Boolean
  val price: Int
  val cost: Int
  def copyTo(hasMoved: Boolean): UnitGamePiece
}
