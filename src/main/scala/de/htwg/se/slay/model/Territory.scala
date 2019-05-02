package de.htwg.se.slay.model

import scala.collection.immutable.HashSet

class Territory {
  private var capital: Field = _
  private var fields: HashSet[Field] = HashSet()

  def setCapital(field: Field): Boolean = {
    field.gamepiece match{
      case _:Capital => capital = field; true
      case _:_ => false
    }
  }

  def addField(field: Field):Unit = fields = fields + field

  def removeField(field: Field):Unit = fields = fields - field
}
