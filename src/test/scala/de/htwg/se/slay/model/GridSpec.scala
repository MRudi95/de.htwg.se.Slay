package de.htwg.se.slay.model

import org.scalatest._

class GridSpec extends WordSpec with Matchers{
  "A Grid contains all the Fields of the Map and" when{
    "created" should{
      val playr = Player("","")
      val field = new Field(playr)
      val fields = Vector[Field](field, field, field, field, field, field)
      val grid = Grid(fields, 2, 3)
      "be a Vector of Fields" in{
        grid should be(fields)
      }
      "have a Row Index" in{
        grid.rowIdx should be(2)
      }
      "have a Column Index" in{
        grid.colIdx should be(3)
      }
      "be able to be indexed directly" in{
        grid(2) should be(field)
      }
      "unapply for code coverage" in {
        Grid.unapply(grid).get should be((fields, 2, 3))
      }
    }
  }
}
