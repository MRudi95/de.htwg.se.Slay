package de.htwg.se.slay.model.gamePieces

import org.scalatest._

class GamePieceSpec extends WordSpec with Matchers {
  "A GamePiece" when{
    "it is movable" should{
      val piece = GamePiece(true)

      "should be true" in{
        piece.movable should be (true)
      }
    }
    "it is not movable" should{
      val piece = GamePiece(false)
      "should be false" in{
        piece.movable should be (false)
      }
    }
  }
}
