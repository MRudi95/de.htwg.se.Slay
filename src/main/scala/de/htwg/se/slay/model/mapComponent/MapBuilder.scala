package de.htwg.se.slay.model.mapComponent

import de.htwg.se.slay.model.gridComponent.{Field, Grid}

import scala.collection.immutable.HashSet
import scala.io.BufferedSource

trait MapBuilder {
  def gridCreator(mapname:String, typ:String = "main"):(Grid, HashSet[Field])
  protected def readCSV(map: BufferedSource): (Vector[Field], Int)
  protected def setNeighbors(grid: Vector[Field], rowIdx: Int, colIdx: Int): Unit
  protected def setTerritories(grid: Vector[Field]): HashSet[Field]
}
