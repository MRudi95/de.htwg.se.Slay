package de.htwg.se.slay.model

case class Player(name: String, color: String) {
  override def toString:String = name
}