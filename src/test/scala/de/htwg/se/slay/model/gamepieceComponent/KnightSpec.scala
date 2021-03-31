package de.htwg.se.slay.model.gamepieceComponent

import de.htwg.se.slay.model.playerComponent.Player
import org.scalatest._

class KnightSpec extends WordSpec with Matchers {
  "A Knight is the third Unit and" when{
    "new" should{
      val playr = new Player("", "")
      val knight = new Knight(playr)
      "have a nice String representation" in {
        knight.toString should be("3")
      }
      "have a Player it belongs to" in{
        knight.player should be (playr)
      }
      "have a strength of 3" in{
        knight.strength should be (3)
      }
      "have a price of 30" in{
        knight.price should be (30)
      }
      "have a cost of 18" in{
        knight.cost should be (18)
      }
      "not have moved that turn yet and have false" in{
        knight.hasMoved should be (false)
      }
    }
    "it has moved in a turn" should{
      val playr = new Player("", "")
      val knight1 = Knight(playr)
      val knight = knight1.copyTo(true)
      "have moved as true" in{
        knight.hasMoved should be (true)
      }
    }
    "a new turn starts" should{
      val playr = new Player("", "")
      val knight1 = Knight(playr)
      val knight = knight1.copyTo(false)
      "have hasMoved set as true" in{
        knight.hasMoved should be (false)
      }
    }
  }
}
