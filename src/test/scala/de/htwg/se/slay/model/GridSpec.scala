package de.htwg.se.slay.model

import de.htwg.se.slay.model.gridComponent.Grid
import de.htwg.se.slay.model.gridComponent.gridBaseImpl.Field
import de.htwg.se.slay.model.playerComponent.Player
import org.scalatest._

class GridSpec extends WordSpec with Matchers{
  "A Grid contains all the Fields of the Map and" when{
    "created" should{
      val playr = new Player("","")
      val field = new Field(playr)
      val fields = Vector[Field](field, field, field, field, field, field)
      val grid = Grid(fields, 1, 2)
      "be a Vector of Fields" in{
        grid should be(fields)
      }
      "have a Row Index" in{
        grid.rowIdx should be(1)
      }
      "have a Column Index" in{
        grid.colIdx should be(2)
      }
      "be able to be indexed directly" in{
        grid(2) should be(field)
      }
      "unapply for code coverage" in {
        Grid.unapply(grid).get should be((fields, 1, 2))
      }
      val grid2 = Grid(Vector[Field](field), 0, 0)
      "have a String representation" in{
        grid2.toString should be(
          "\n    A  \n" +
          "       \033[0m\n" +
          "1   \033[1;97m   \033[0m\n" +
          "       \033[0m\n")
      }
    }
  }
}
