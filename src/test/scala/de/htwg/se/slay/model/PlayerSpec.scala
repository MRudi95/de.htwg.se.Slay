package de.htwg.se.slay.model

import org.scalatest._

class PlayerSpec extends WordSpec with Matchers {
  "A Player" when {
    "new" should {
      val player = Player("Your Name", "\033[44m")
      "have a name"  in {
        player.name should be("Your Name")
      }

      "have a nice String representation" in {
        player.toString should be("Your Name")
      }
      "have a Color as ANSI escape code" in {
        player.color should be ("\033[44m")
      }
    }
  }


}