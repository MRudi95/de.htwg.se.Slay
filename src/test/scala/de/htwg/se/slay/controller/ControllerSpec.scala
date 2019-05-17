package de.htwg.se.slay.controller

import de.htwg.se.slay.model.{Capital, Player}
import org.scalatest._

class ControllerSpec extends WordSpec with Matchers with PrivateMethodTester {
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

      "be able to check if an Index in the format 'A1' is valid to access the Grid" in{
        //still needs alot of other possibilities
        controller.checkIndex("A1") should be (true)
        controller.checkIndex("D2") should be (true)
        controller.checkIndex("A7") should be (false)
        controller.checkIndex("D9") should be (false)
      }

      "be able to convert an Index in the format 'A1' to an Integer Index" in{
        controller.convertIndex("A1") should be (0)
        controller.convertIndex("B1") should be (1)
        controller.convertIndex("C1") should be (2)
        controller.convertIndex("D4") should be (15)
      }

      "be able to convert an Index from Integer to the format 'A1'" in{
        controller.convertIndex(0) should be ("A1")
        controller.convertIndex(1) should be ("B1")
        controller.convertIndex(2) should be ("C1")
        controller.convertIndex(15) should be ("D4")
      }

      controller.moneymoney()
      "have money added to the capitals" in{
        controller.grid(2).gamepiece.asInstanceOf[Capital].balance should be (14)
        controller.grid(3).gamepiece.asInstanceOf[Capital].balance should be (11)
      }
    }
  }
}
