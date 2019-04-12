package de.htwg.se.slay.model

import org.scalatest._

class FieldSpec extends WordSpec with Matchers {
  "A Field" when {
    "new" should {
      val field = Field(0, "grün", "Player1")
      "have index" in {
        field.i should be(0)
      }
      "have color" in {
        field.str should be("grün")
      }
      "have Player" in {
        field.str1 should be("Pl")
      }
    }
    "new Field" should {
      val field = Field(1, "grün", "Player1")
      "have index" in {
        field.i should be(1)
      }
      "have color" in {
        field.str should be("grün")
      }
      "have Player" in {
        field.str1 should be("Player1")
      }
    }
    "has new owner" should {
      val field = Field(1, "grün", "Player1")
      "" in{
        field.newOwner(1, "red", "Player2") should be(Field(1, "red", "Player2"))
      }

    }
  }}
