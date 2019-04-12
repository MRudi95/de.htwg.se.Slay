package de.htwg.se.slay

import de.htwg.se.slay.model.Player

object Slay{
  def main(args: Array[String]) : Unit = {
    val student = Player("Yvonne und Rudi!!!!")
    println("Hello, " + student.name)
  }
}