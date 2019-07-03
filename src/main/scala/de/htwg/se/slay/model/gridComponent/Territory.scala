package de.htwg.se.slay.model.gridComponent

import de.htwg.se.slay.model.gamepieceComponent.{Capital, UnitGamePiece}

import scala.collection.immutable.HashSet

class Territory {
  private var _capital: Capital = _
  private var _fields: HashSet[Field] = HashSet()
  var armyCost: Int = 0

  def setCapital(field: Field): Boolean = {
    field.gamepiece match{
      case capital:Capital => _capital = capital; true
      case _ => false
    }
  }

  def addField(field: Field):Unit = _fields += field
  def removeField(field: Field):Unit = _fields -= field
  def size: Int = _fields.size

  def addUnit(unit: UnitGamePiece):Unit = {
    armyCost += unit.cost
  }
  def removeUnit(unit: UnitGamePiece):Unit = {
    armyCost -= unit.cost
  }

  def capital: Capital = _capital
  def fields: HashSet[Field] = _fields

}