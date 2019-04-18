package de.htwg.se.slay.model

import org.scalatest._

class GraveSpec extends WordSpec with Matchers {
  "A Grave is a neutral GamePiece that doesnt belong to any Player and" when{
    "new" should{
      val grave = Grave()
      "have no Player" in{
        grave.player should be (null)
      }
      "have a strength of 0" in{
        grave.strength should be (0)
      }
    }
  }
}
