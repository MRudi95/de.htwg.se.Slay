package de.htwg.se.slay.model

case class Player(name: String, col: String) {

  def color: String = col

  override def toString: String = name
}