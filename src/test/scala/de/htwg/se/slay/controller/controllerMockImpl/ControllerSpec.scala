package de.htwg.se.slay.controller.controllerMockImpl

import de.htwg.se.slay.controller.controllerComponent.controllerMockImpl.Controller

import org.scalatest._

class ControllerSpec extends WordSpec with Matchers{

  "A Controller controlls the game and" when {
    "new" should {
      val controller = new Controller()
      "have Player0 as default" in {
        controller.players(0).name should be("Player0")
        controller.players(1).name should be("Player 1")
        controller.players(2).name should be("Player 2")
      }

      controller.gridToString
      controller.addPlayer(controller.players(0))
      controller.changeName("", 0)
      controller.createGrid("","")
      controller.seeBalance(1)
      controller.buyPeasant(1)
      controller.placeCastle(1)
      controller.combineUnit(1, 2)
      controller.moveUnit(1, 2)
      controller.surrender()
      controller.moneymoney()
      controller.nextturn()
      controller.undo()
      controller.redo()
      controller.save("")
      controller.load("")

      "Have state 0" in {
        controller.state should be(0)
      }
    }
  }
}
