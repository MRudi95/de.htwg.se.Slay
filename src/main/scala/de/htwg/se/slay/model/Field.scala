package de.htwg.se.Slay.model

case class Field(i: Int, str: String, str1: String) {
  def newOwner(index:Int, color: String, name: String):Field = Field(index, color, name)
}
