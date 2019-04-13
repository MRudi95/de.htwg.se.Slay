package de.htwg.se.slay.model

import org.scalatest._

class TowerSpec extends WordSpec with Matchers {
  "A Tower is an immovable GamePiece that can be used for defense and" when{
    "new" should{
      val playr = Player("","")
      val tower = Tower(playr)
      "have a Player it belongs to" in{
        tower.player should be (playr)
      }
      "have a price of 15" in{
        tower.price should be (15)
      }
      "have a strength of 2" in{
        tower.strength should be (2)
      }
    }
  }
}
