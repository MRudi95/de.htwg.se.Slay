package de.htwg.se.slay.controller

object StateStartUp {
  var state = idle()

  def handle(e: StartUp, c: Controller): Unit ={
    e match {
      case _:WelcomeScreen => state = welcome(c)
      case r:ReadPlayerName => state = readName(r.player ,c)
    }
    state
  }

  def idle(): Unit = {}

  def welcome(c: Controller): Unit = {
    c.notifyObservers(WelcomeEvent())
  }

  def readName(player:Int, c: Controller): Unit = {
    c.notifyObservers(ReadPlayerEvent(player))
  }
}
