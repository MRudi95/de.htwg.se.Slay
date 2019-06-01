package de.htwg.se.slay.controller

import de.htwg.se.slay.model.{Capital, Player}
import org.scalatest._

class ControllerSpec extends WordSpec with Matchers{
  "A Controller controlls the game and" when{
    "new" should{
      val controller = new Controller
      "have Player0 as default" in{
        controller.players(0).name should be ("Player0")
      }
      val playr1 = Player("1","")
      controller.addPlayer(playr1)
      "have added Players" in{
        controller.players(1) should be (playr1)
      }
      "have no Grid yet" in{
        controller.grid should be (null)
      }
      "have no Capitals yet" in{
        controller.capitals should be (null)
      }
    }
    "creating a Grid" should{
      val controller = new Controller
      val playr1 = Player("1","")
      val playr2 = Player("2","")
      controller.addPlayer(playr1)
      controller.addPlayer(playr2)

      controller.createGrid("Test", "test")
      "have a Grid" in{
        controller.grid should not be null
      }
      "have Capitals" in{
        controller.capitals should not be null
      }
      "have a String representation for the Grid" in{
        controller.gridToString should be (controller.grid.toString)
      }

      controller.moneymoney()
      "have money added to the capitals" in{
        controller.grid(2).gamepiece.asInstanceOf[Capital].balance should be (14)
        controller.grid(3).gamepiece.asInstanceOf[Capital].balance should be (11)
      }
    }
  }
}
