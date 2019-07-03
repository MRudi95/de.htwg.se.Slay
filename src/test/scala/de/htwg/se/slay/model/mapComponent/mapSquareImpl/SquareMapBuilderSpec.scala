package de.htwg.se.slay.model.mapComponent.mapSquareImpl

import de.htwg.se.slay.model.gridComponent.{FieldInterface, GridInterface}
import de.htwg.se.slay.model.playerComponent.Player
import org.scalatest.{Matchers, WordSpec}

import scala.collection.immutable.HashSet

class SquareMapBuilderSpec extends WordSpec with Matchers {
  "A MapReader" should {
    val players: Vector[Player] = Vector(
      new Player("0",""),
      new Player("1",""),
      new Player("2",""))
    val reader = new SquareMapBuilder(players)
    val (grid: GridInterface, capitals: HashSet[FieldInterface]) = reader.gridCreator("Test", "test")

    "read a Map from a .csv into a Grid and assigns it to its given players" in{
      grid(0).owner should be(players(0))
      grid(1).owner should be(players(1))
      grid(2).owner should be(players(2))
      grid.length should be(8)
    }
    "read the Fields with Capital GamePieces on it" in{
      capitals.contains(grid(2)) should be (true)
      capitals.contains(grid(3)) should be (true)
      capitals.contains(grid(5)) should be (false)
      capitals.size should be (2)
    }
    "assign Neighbor relationships to the Fields in the Grid" in{
      grid(0).neighbors.neighborEast should be(Option(grid(1)))
      grid(1).neighbors.neighborWest should be(Option(grid(0)))

      grid(0).neighbors.neighborWest should be(None)
      grid(0).neighbors.neighborSouth should be(Option(grid(4)))
      grid(0).neighbors.neighborNorth should be(None)
    }
    "assign Territory relationships to the Fields in the Grid" in{
      grid(2).territory.fields.contains(grid(2)) should be (true)
      grid(2).territory.fields.contains(grid(5)) should be (true)
      grid(2).territory.fields.contains(grid(6)) should be (true)
      grid(2).territory.fields.contains(grid(7)) should be (true)
      grid(2).territory.fields.contains(grid(1)) should be (false)
      grid(2).territory.capital should be (grid(2).gamepiece)
    }

    "special case \"U-formation\"" in{
      reader.gridCreator("TestSpecialCases", "test")
    }
  }
}
