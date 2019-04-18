package de.htwg.se.slay.model

import org.scalatest._

class TreeSpec extends WordSpec with Matchers {
  "A Tree is a neutral GamePiece that doesnt belong to any Player and" when{
    "new" should{
      val tree = Tree()
      "have no Player" in{
        tree.player should be (null)
      }
      "have a strength of 0" in{
        tree.strength should be (0)
      }
    }
  }
}
