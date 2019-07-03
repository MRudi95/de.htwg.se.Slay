package de.htwg.se.slay.model.mapComponent

import de.htwg.se.slay.model.gridComponent.{Field, Grid}

import scala.collection.immutable.HashSet

trait MapInterface {
  def gridCreator(mapname:String, typ:String = "main"):(Grid, HashSet[Field])
}