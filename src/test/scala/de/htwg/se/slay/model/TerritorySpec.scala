package de.htwg.se.slay.model

import org.scalatest._

import scala.collection.immutable.HashSet

class TerritorySpec extends WordSpec with Matchers {
  "A Territory is a set of connected Fields that belong to one Player and" when{
    "created" should{
      val ter = new Territory
      "have no capital yet" in{
        ter.capital should be (null)
      }
      "have no Fields yet" in{
        ter.fields should be(HashSet[Field]())
      }
      "have a size of 0" in{
        ter.size() should be(0)
      }

      val playr = Player("", "")
      val field = new Field(playr)
      val ter2 = new Territory
      ter2.addField(field)
      "have added Fields" in{
        ter2.fields.contains(field) should be (true)
        ter2.size() should be(1)
      }

      val ter3 = new Territory
      ter3.addField(field)
      ter3.removeField(field)
      "not have removed Fields" in{
        ter3.fields.contains(field) should be (false)
        ter3.size() should be(0)
      }
    }
  }
}

