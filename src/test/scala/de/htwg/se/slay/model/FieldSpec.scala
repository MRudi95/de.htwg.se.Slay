package de.htwg.se.slay.model

import org.scalatest._

class FieldSpec extends WordSpec with Matchers {
  "A Field is the central property Players try to own to win the game and" when {
    "new" should {
      val playr = Player("", "")
      val field = new Field(playr)
      "have a Player who owns the Field" in {
        field.owner should be(playr)
      }
      "have no GamePiece on it" in{
        field.gamepiece should be(null)
      }
      "have no neighboring Fields set yet" in{
        field.neighbors should be(null)
      }
    }
    "its owner changes" should{
      val playr = Player("", "")
      val field = new Field(playr)
      val playr2 = Player("","")
      field.owner = playr2
      "have the new owner" in{
        field.owner should be(playr2)
      }
    }
    "its GamePiece change" should{
      val playr = Player("", "")
      val field = new Field(playr)
      val gp = Tree()
      field.gamepiece = gp
      "have the new GamePiece" in{
        field.gamepiece should be(gp)
      }
    }
    "setting the neighboring Fields" should{
      val playr = Player("", "")
      val fieldOne = new Field(playr)
      val fieldTwo = new Field(playr)
      val neighbors = Vector[Field](fieldTwo, fieldOne, fieldOne, fieldTwo)
      val bool = fieldOne.setNeighbors(neighbors)
      "only be set with a Vector of 4 Fields" in{
        fieldOne.neighbors should be(neighbors)
        bool should be(true)
      }
      val neighbors2 = Vector[Field](fieldOne)
      val bool2 = fieldTwo.setNeighbors(neighbors2)
      "not be set with a Vector that doesnt have exactly 4 Fields" in{
        fieldTwo.neighbors should be(null)
        bool2 should be(false)
      }
      val neighbors3 = Vector[Field](fieldOne, fieldTwo, fieldOne, fieldTwo)
      val bool3 = fieldOne.setNeighbors(neighbors3)
      "not be set more than one time" in{
        fieldOne.neighbors should be(neighbors)
        bool3 should be(false)
      }
    }
  }
}
