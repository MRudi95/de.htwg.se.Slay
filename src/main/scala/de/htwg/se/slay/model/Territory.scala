package de.htwg.se.slay.model

import scala.collection.immutable.HashSet

class Territory {
  private var _capital: Field = _
  private var _fields: HashSet[Field] = HashSet()

  def setCapital(field: Field): Boolean = {
    field.gamepiece match{
      case _:Capital => _capital = field; true
      case _ => false
    }
  }

  def addField(field: Field):Unit = _fields += field

  def removeField(field: Field):Unit = _fields -= field

  def length():Int = _fields.size

  def capital: Field = _capital
  def fields: HashSet[Field] = _fields
}
