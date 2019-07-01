package de.htwg.se.slay.controller

import de.htwg.se.slay.model.Player
import org.scalatest.{Matchers, WordSpec}

class StatePlayerTurnSpec extends WordSpec with Matchers{
  "The StatePlayerTurn is supposed to handle PlayerTurn Events as a state and" should{
    "have an idle state at the beginning" in{
      StatePlayerTurn.state should be (StatePlayerTurn.idle())
    }

    val controller = new Controller
    controller.createGrid("Test", "test")

    "handle Player0Turn" in {
      StatePlayerTurn.handle(Player0Turn(), controller)
    }
    "handle Player1Turn" in {
      StatePlayerTurn.handle(Player1Turn(), controller)
    }
    "handle Player2Turn" in {
      StatePlayerTurn.handle(Player2Turn(), controller)
    }
  }
}
