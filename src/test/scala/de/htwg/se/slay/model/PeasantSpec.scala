package de.htwg.se.slay.model

import org.scalatest._

class PeasantSpec extends WordSpec with Matchers {
  "A Peasant is the first and weakest Unit and" when{
    "new" should{
      val playr = Player("", "")
      val peasant = new Peasant(playr)
      "have a nice String representation" in {
        peasant.toString should be("1")
      }
      "have a Player it belongs to" in{
        peasant.player should be (playr)
      }
      "have a strength of 1" in{
        peasant.strength should be (1)
      }
      "have a price of 10" in{
        peasant.price should be (10)
      }
      "have a cost of 2" in{
        peasant.cost should be (2)
      }
      "not have moved that turn yet and have false" in{
        peasant.hasMoved should be (false)
      }
    }
    "it has moved in a turn" should{
      val playr = Player("", "")
      val peasant = new Peasant(playr)
      peasant.hasMoved = true
      "have moved as true" in{
        peasant.hasMoved should be (true)
      }
    }
    "a new turn starts" should{
      val playr = Player("", "")
      val peasant = new Peasant(playr)
      peasant.hasMoved = false
      "have hasMoved set as true" in{
        peasant.hasMoved should be (false)
      }
    }
  }
}
