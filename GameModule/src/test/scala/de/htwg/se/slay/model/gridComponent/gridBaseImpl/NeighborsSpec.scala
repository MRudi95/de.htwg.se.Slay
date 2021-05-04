package de.htwg.se.slay.model.gridComponent.gridBaseImpl

import de.htwg.se.slay.model.playerComponent.Player
import org.scalatest.{Matchers, WordSpec}

class NeighborsSpec extends WordSpec with Matchers {
  "A Neighbors object is all the neighboring Fields surrounding a specific Field that" when{
    "created" should{
      val playr = new Player("", "")
      val fieldN = new Field(playr)
      val fieldW = new Field(playr)
      val fieldE = null
      val fieldS = new Field(playr)
      val neighbors = Neighbors(Option(fieldN), Option(fieldW), Option(fieldE), Option(fieldS))
      "have 4 Fields, which should be located in the corresponding cardinal directions," in{
        neighbors.neighborNorth should be(Option(fieldN))
        neighbors.neighborWest should be(Option(fieldW))
        neighbors.neighborEast should be(Option(fieldE))
        neighbors.neighborSouth should be(Option(fieldS))
      }
      "have a neighbor set to null, when there is no neighbor in that direction" in{
        neighbors.neighborEast should be(None)
      }
      val it = neighbors.iterator
      "be Iterable with neighborNorth being the first and neighborSouth being the last element" in{
        it.next() should be(Option(fieldN))
        it.next() should be(Option(fieldW))
        it.next() should be(Option(fieldE))
        it.next() should be(Option(fieldS))
        it.hasNext should be(false)
      }
    }
  }
}
