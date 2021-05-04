package de.htwg.se.slay.util

import de.htwg.se.slay.model.gridComponent.{FieldInterface, GridInterface}

import scala.collection.immutable.HashSet
import scala.io.BufferedSource

trait MapBuilder {
  def gridCreator(mapname:String, typ:String = "main"):(GridInterface, HashSet[FieldInterface])
  protected def readCSV(map: BufferedSource): (Vector[FieldInterface], Int)
  protected def setNeighbors(grid: Vector[FieldInterface], rowIdx: Int, colIdx: Int): Unit
  protected def setTerritories(grid: Vector[FieldInterface]): HashSet[FieldInterface]
}
