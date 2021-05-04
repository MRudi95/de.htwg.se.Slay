package de.htwg.se.slay.model.gridComponent.gridBaseImpl

import de.htwg.se.slay.model.gamepieceComponent.{Capital, UnitGamePiece}
import de.htwg.se.slay.model.gridComponent.{FieldInterface, TerritoryInterface}

import scala.collection.immutable.HashSet

class Territory extends TerritoryInterface {
  private var _capital: Capital = _
  private var _fields: HashSet[FieldInterface] = HashSet()
  var armyCost: Int = 0

  def setCapital(field: FieldInterface): Boolean = {
    field.gamepiece match{
      case capital:Capital => _capital = capital; true
      case _ => false
    }
  }

  def addField(field: FieldInterface):Unit = _fields += field
  def removeField(field: FieldInterface):Unit = _fields -= field
  def size: Int = _fields.size

  def addUnit(unit: UnitGamePiece):Unit = {
    armyCost += unit.cost
  }
  def removeUnit(unit: UnitGamePiece):Unit = {
    armyCost -= unit.cost
  }

  def capital: Capital = _capital
  def fields: HashSet[FieldInterface] = _fields

}
