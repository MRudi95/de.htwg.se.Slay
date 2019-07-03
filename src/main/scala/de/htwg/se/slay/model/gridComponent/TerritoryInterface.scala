package de.htwg.se.slay.model.gridComponent

import de.htwg.se.slay.model.gamepieceComponent.{Capital, UnitGamePiece}

import scala.collection.immutable.HashSet

trait TerritoryInterface{
  def capital: Capital
  def fields: HashSet[FieldInterface]
  var armyCost: Int

  def addUnit(unit: UnitGamePiece):Unit
  def removeUnit(unit: UnitGamePiece):Unit

  def addField(field: FieldInterface):Unit
  def removeField(field: FieldInterface):Unit
  def size: Int

  def setCapital(field: FieldInterface): Boolean
}

trait TerritoryFactory{
  def create(): TerritoryInterface
}