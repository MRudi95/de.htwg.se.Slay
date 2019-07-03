package de.htwg.se.slay.model

import de.htwg.se.slay.model.gamepieceComponent.NoPiece
import org.scalatest._

class NoPieceSpec extends WordSpec with Matchers {
  "NoPiece is the default GamePiece when there is no GamePiece on a Field and" when{
    "new" should{
      val noPiece = NoPiece()
      "have a nice String representation" in {
        noPiece.toString should be(" ")
      }
      "have no Player" in{
        noPiece.player should be (null)
      }
      "have a strength of 0" in{
        noPiece.strength should be (0)
      }
      "unapply for code coverage" in {
        NoPiece.unapply(noPiece) should be(true)
      }
    }
  }
}