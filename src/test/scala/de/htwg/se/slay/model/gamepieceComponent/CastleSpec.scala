package de.htwg.se.slay.model.gamepieceComponent

import de.htwg.se.slay.model.playerComponent.Player
import org.scalatest._

class CastleSpec extends WordSpec with Matchers {
  "A Castle is an immovable GamePiece that can be used for defense and" when{
    "new" should{
      val playr = new Player("","")
      val castle = Castle(playr)
      "have a nice String representation" in {
        castle.toString should be("B")
      }
      "have a Player it belongs to" in{
        castle.player should be (playr)
      }
      "have a price of 15" in{
        castle.price should be (15)
      }
      "have a strength of 2" in{
        castle.strength should be (2)
      }
      "unapply for code coverage" in {
        Castle.unapply(castle).get should be(playr)
      }
    }
  }
}
