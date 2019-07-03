package de.htwg.se.slay.model.gridComponent

import de.htwg.se.slay.model.gamepieceComponent.{Capital, GamePiece, UnitGamePiece}
import de.htwg.se.slay.model.playerComponent.Player

import scala.collection.immutable.HashSet

trait GridInterface extends IndexedSeq[FieldInterface]{
  override def length: Int
  override def apply(idx: Int):FieldInterface

  override def toString: String

  def rowIdx: Int
  def colIdx: Int
}


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


trait NeighborInterface extends Iterable[FieldInterface] {
  def neighborNorth: FieldInterface

  def neighborWest: FieldInterface

  def neighborEast: FieldInterface

  def neighborSouth: FieldInterface

  override def iterator: Iterator[FieldInterface] = {
    Iterator[FieldInterface](neighborNorth, neighborWest, neighborEast, neighborSouth)
  }
}