package de.htwg.se.slay.controller

import de.htwg.se.slay.controller.controllerComponent.{ControllerInterface, ReadPlayerEvent, WelcomeEvent}

object StateStartUp {
  var state = idle()

  def handle(e: StartUp, c: ControllerInterface): Unit ={
    e match {
      case _:WelcomeScreen => state = welcome(c)
      case r:ReadPlayerName => state = readName(r.player ,c)
    }
    state
  }

  def idle(): Unit = {}

  def welcome(c: ControllerInterface): Unit = {
    c.notifyObservers(WelcomeEvent())
  }

  def readName(player:Int, c: ControllerInterface): Unit = {
    if(player <= 2)
      c.notifyObservers(ReadPlayerEvent(player))
    else
      c.createGrid("Map1")
  }

}



trait StartUp{}

case class WelcomeScreen() extends StartUp
case class ReadPlayerName(player:Int) extends StartUp