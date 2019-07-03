package de.htwg.se.slay.model.mapComponent

import de.htwg.se.slay.model.gridComponent.{FieldInterface, GridInterface}
import de.htwg.se.slay.model.playerComponent.Player

import scala.collection.immutable.HashSet

trait MapInterface {
  def gridCreator(mapname:String, typ:String = "main"):(GridInterface, HashSet[FieldInterface])
}

trait MapFactory{
  def create(players: Vector[Player]): MapInterface
}