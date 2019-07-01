package de.htwg.se.slay.controller

import de.htwg.se.slay.model._
import org.scalatest._

class ControllerSpec extends WordSpec with Matchers{
  "A Controller controlls the game and" when{
    "new" should{
      val controller = new Controller
      "have Player0 as default" in{
        controller.players(0).name should be ("Player0")
      }
      val playr1 = new Player("1","")
      controller.addPlayer(playr1)
      "have added Players" in{
        controller.players(3) should be (playr1)
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

    "changing a players name" should{
      val ctrl = new Controller
      ctrl.state = 1
      ctrl.changeName("newname", 1)
      ctrl.changeName("namenew", 2)
      "have the changed names" in{
        ctrl.players(1) should be ("newname")
        ctrl.players(2) should be ("namenew")
      }
    }

    "doing actions on the game" should{
      val controller = new Controller
      controller.createGrid("Test", "test")

      "notify the Observers with the balance of a capital" in{
        controller.state = 2
        controller.seeBalance(2)
      }

      "place a Peasant, when the owner is right, NoPiece or Tree is on the Field," +
        " and the territory has a balance of at least 10" in {
        controller.state = 2
        controller.buyPeasant(5)
        controller.grid(5).gamepiece.getClass should be (classOf[Peasant])

        controller.grid(5).gamepiece = Tree()
        controller.grid(2).gamepiece.asInstanceOf[Capital].balance = 5
        controller.buyPeasant(5) //not enough money
        controller.grid(5).gamepiece.getClass should be (classOf[Tree])

        controller.buyPeasant(2) //wrong gamepiece
        controller.grid(2).gamepiece.getClass should be (classOf[Capital])
      }

      "place a Castle, when the owner is right, NoPiece or Tree is on the Field," +
        " and the territory has a balance of at least 15" in {
        controller.state = 2
        controller.grid(2).gamepiece.asInstanceOf[Capital].balance = 15
        controller.placeCastle(6)
        controller.grid(6).gamepiece.getClass should be (classOf[Castle])
      }

      "combine two Units, when the owner is right, and the Units are combinable" in{
        controller.state = 2

        controller.grid(5).gamepiece = new Peasant(controller.grid(5).owner)
        controller.grid(6).gamepiece = new Peasant(controller.grid(6).owner)
        controller.combineUnit(5, 6)
        controller.grid(5).gamepiece.getClass should be (classOf[Spearman])
        controller.grid(6).gamepiece.getClass should be (classOf[NoPiece])

        controller.grid(5).gamepiece = new Spearman(controller.grid(5).owner)
        controller.grid(6).gamepiece = new Spearman(controller.grid(6).owner)
        controller.combineUnit(5, 6)
        controller.grid(5).gamepiece.getClass should be (classOf[Baron])
        controller.grid(6).gamepiece.getClass should be (classOf[NoPiece])

        controller.grid(5).gamepiece = new Peasant(controller.grid(5).owner)
        controller.grid(6).gamepiece = new Spearman(controller.grid(6).owner)
        controller.combineUnit(5, 6)
        controller.grid(5).gamepiece.getClass should be (classOf[Knight])
        controller.grid(6).gamepiece.getClass should be (classOf[NoPiece])

        controller.grid(5).gamepiece = new Peasant(controller.grid(5).owner)
        controller.grid(6).gamepiece = new Knight(controller.grid(6).owner)
        controller.combineUnit(5, 6)
        controller.grid(5).gamepiece.getClass should be (classOf[Baron])
        controller.grid(6).gamepiece.getClass should be (classOf[NoPiece])

        controller.grid(5).gamepiece = new Knight(controller.grid(5).owner)
        controller.grid(6).gamepiece = new Knight(controller.grid(6).owner)
        controller.combineUnit(5, 6) //wrong Units
        controller.grid(5).gamepiece.getClass should be (classOf[Knight])
        controller.grid(6).gamepiece.getClass should be (classOf[Knight])
      }

      "be able to undo and redo Commands" in{
        val gp = controller.grid(7).gamepiece
        controller.state = 2
        controller.grid(2).gamepiece.asInstanceOf[Capital].balance = 10
        controller.buyPeasant(7)
        controller.grid(7).gamepiece.getClass should be (classOf[Peasant])

        controller.undo()
        controller.grid(7).gamepiece should be (gp)

        controller.redo()
        controller.grid(7).gamepiece.getClass should be (classOf[Peasant])
      }
    }
  }
}
