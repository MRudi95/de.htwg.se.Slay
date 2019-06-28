package de.htwg.se.slay.model

import org.scalatest._

class CapitalSpec extends WordSpec with Matchers {
  "A Capital is the central building of a territory and" when{
    "new" should{
      val playr = Player("", "")
      val capital = new Capital(playr)
      "have a nice String representation" in {
        capital.toString should be("C")
      }
      "have a Player it belongs to" in{
        capital.player should be (playr)
      }
      "have a (defensive) strength of 1" in{
        capital.strength should be (1)
      }
      "have a balance of 10" in{
        capital.balance should be (10)
      }
    }
  }
}
