package de.htwg.se.slay.model

import de.htwg.se.slay.model.gamepieceComponent.Tree
import org.scalatest._

class TreeSpec extends WordSpec with Matchers {
  "A Tree is a neutral GamePiece that does not belong to any Player and" when{
    "new" should{
      val tree = Tree()
      "have a nice String representation" in {
        tree.toString should be("T")
      }
      "have no Player" in{
        tree.player should be (null)
      }
      "have a strength of 0" in{
        tree.strength should be (0)
      }
      "unapply for code coverage" in {
        Tree.unapply(tree) should be(true)
      }
    }
  }
}
