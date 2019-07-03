package de.htwg.se.slay.model.mapComponent

import de.htwg.se.slay.model.gridComponent.{Field, FieldInterface, Grid, GridInterface}

import scala.collection.immutable.HashSet

trait MapInterface {
  def gridCreator(mapname:String, typ:String = "main"):(GridInterface, HashSet[FieldInterface])
}
