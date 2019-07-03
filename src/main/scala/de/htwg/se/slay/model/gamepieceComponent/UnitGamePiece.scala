package de.htwg.se.slay.model.gamepieceComponent

trait UnitGamePiece extends GamePiece{
  var hasMoved: Boolean
  val price: Int
  val cost: Int
}
