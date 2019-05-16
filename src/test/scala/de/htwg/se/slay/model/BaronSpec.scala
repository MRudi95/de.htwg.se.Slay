package de.htwg.se.slay.model

import org.scalatest._

class BaronSpec extends WordSpec with Matchers {
  "A Baron is the fourth and strongesst Unit and" when{
    "new" should{
      val playr = Player("", "")
      val baron = new Baron(playr)
      "have a nice String representation" in {
        baron.toString should be("4")
      }
      "have a Player it belongs to" in{
        baron.player should be (playr)
      }
      "have a strength of 4" in{
        baron.strength should be (4)
      }
      "have a price of 40" in{
        baron.price should be (40)
      }
      "have a cost of 54" in{
        baron.cost should be (54)
      }
      "not have moved that turn yet and have false" in{
        baron.hasMoved should be (false)
      }
    }
    "it has moved in a turn" should{
      val playr = Player("", "")
      val baron = new Baron(playr)
      baron.hasMoved = true
      "have moved as true" in{
        baron.hasMoved should be (true)
      }
    }
    "a new turn starts" should{
      val playr = Player("", "")
      val baron = new Baron(playr)
      baron.hasMoved = false
      "have hasMoved set as true" in{
        baron.hasMoved should be (false)
      }
    }
  }
}
