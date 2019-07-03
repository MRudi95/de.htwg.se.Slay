package de.htwg.se.slay.controller

import de.htwg.se.slay.controller.controllerComponent.controllerBaseImpl.Controller
import org.scalatest.{Matchers, WordSpec}

class StateStartUpSpec extends WordSpec with Matchers{
  "The StatePlayerTurn is supposed to handle WelcomeScreen and ReadPlayerName Events as a state and" should{
    "have an idle state at the beginning" in{
      StateStartUp.state should be (StateStartUp.idle())
    }

    val controller = new Controller

    "handle WelcomeScreen" in {
      StateStartUp.handle(WelcomeScreen(), controller)
    }
    "handle ReadPlayerName" in {
      StateStartUp.handle(ReadPlayerName(1), controller)
      StateStartUp.handle(ReadPlayerName(2), controller)
      StateStartUp.handle(ReadPlayerName(3), controller)
    }
  }
}