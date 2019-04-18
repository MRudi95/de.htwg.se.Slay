package de.htwg.se.slay.model

import org.scalatest._

class GridSpec extends WordSpec with Matchers{
  "A Grid contains all the Fields of the Map and" when{
    "created" should{
      val playr = Player("","")
      val field = new Field(playr)
      val fields = Vector[Field](field, field, field, field, field, field)
      val grid = Grid(fields, 2, 3)
      "have a Vector of Fields" in{
        grid.grid should be(fields)
      }
      "have a Row Index" in{
        grid.rowIdx should be(2)
      }
      "have a Column Index" in{
        grid.colIdx should be(3)
      }
    }
  }
}
