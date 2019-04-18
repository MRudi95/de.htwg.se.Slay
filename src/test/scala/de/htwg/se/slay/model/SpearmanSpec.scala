package de.htwg.se.slay.model

import org.scalatest._

class SpearmanSpec extends WordSpec with Matchers {
  "A Spearman is the second Unit and" when{
    "new" should{
      val playr = Player("", "")
      val spearman = new Spearman(playr)
      "have a Player it belongs to" in{
        spearman.player should be (playr)
      }
      "have a strength of 2" in{
        spearman.strength should be (2)
      }
      "have a price of 20" in{
        spearman.price should be (20)
      }
      "have a cost of 6" in{
        spearman.cost should be (6)
      }
      "not have moved that turn yet and have false" in{
        spearman.hasMoved should be (false)
      }
    }
    "it has moved in a turn" should{
      val playr = Player("", "")
      val spearman = new Spearman(playr)
      spearman.hasMoved = true
      "have moved as true" in{
        spearman.hasMoved should be (true)
      }
    }
    "a new turn starts" should{
      val playr = Player("", "")
      val spearman = new Spearman(playr)
      spearman.hasMoved = false
      "have hasMoved set as true" in{
        spearman.hasMoved should be (false)
      }
    }
  }
}