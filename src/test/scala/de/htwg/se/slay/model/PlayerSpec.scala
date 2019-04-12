package de.htwg.se.slay.model

import org.scalatest._

class PlayerSpec extends WordSpec with Matchers {
  "A Player" when {
    "new" should {
      val player = Player("Your Name", "color")
      "have a name"  in {
        player.name should be("Your Name")
      }
      "have a color" in {
        player.col should be("color")
      }
      "have a nice String representation" in {
        player.toString should be("Your Name")
      }
    }
  }


}