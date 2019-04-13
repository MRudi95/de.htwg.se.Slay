package de.htwg.se.slay.model

import org.scalatest._

class GamePieceSpec extends WordSpec with Matchers {
  "A GamePiece" when{
    "created without parameter" should{
      val piece = GamePiece()
      "have movable false" in{
        piece.movable should be (false)
      }
      "and player as null" in{
        piece.player should be (null)
      }
    }
    "it has a Player" should{
      val playr = Player("Name", "Color")
      val piece = GamePiece(player = playr)
      "have a Player" in{
        piece.player should be (playr)
      }
    }
    "it is a neutral piece" should{
      "not have a Player" in{
        val piece = GamePiece(player = null)
        piece.player should be (null)
      }
    }
    "it is movable" should{
      val piece = GamePiece(movable = true)
      "have movable true" in{
        piece.movable should be (true)
      }
    }
    "it is not movable" should{
      val piece = GamePiece(movable = false)
      "have movable false" in{
        piece.movable should be (false)
      }
    }
  }
}
