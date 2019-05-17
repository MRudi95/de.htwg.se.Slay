package de.htwg.se.slay.model

import org.scalatest.{Matchers, WordSpec}

import scala.collection.immutable.HashSet

class MapReaderSpec extends WordSpec with Matchers {
  "A MapReader" should {
    val players: Vector[Player] = Vector(Player("0",""), Player("1",""), Player("2",""))
    val reader = new MapReader(players)
    val (grid: Grid, capitals: HashSet[Field]) = reader.gridCreator("Test", "test")

    "read a Map from a .csv into a Grid and assigns it to its given players" in{
      grid(0).owner should be(players(0))
      grid(1).owner should be(players(1))
      grid(2).owner should be(players(2))
      grid.length should be(6)
    }
    "read the Fields with Capital GamePieces on it" in{
      capitals.contains(grid(2)) should be (true)
      capitals.contains(grid(4)) should be (false)
      capitals.size should be (1)
    }
    "assign Neighbor relationships to the Fields in the Grid" in{
      grid(0).neighbors.neighborEast should be(grid(1))
      grid(1).neighbors.neighborWest should be(grid(0))

      grid(0).neighbors.neighborWest should be(null)
      grid(0).neighbors.neighborSouth should be(grid(3))
      grid(0).neighbors.neighborNorth should be(null)
    }
    "assign Territory relationships to the Fields in the Grid" in{
      grid(2).territory.fields.contains(grid(2)) should be (true)
      grid(2).territory.fields.contains(grid(5)) should be (true)
      grid(2).territory.fields.contains(grid(4)) should be (true)
      grid(2).territory.fields.contains(grid(1)) should be (false)
      grid(2).territory.capital should be (grid(2))
    }
  }
}
