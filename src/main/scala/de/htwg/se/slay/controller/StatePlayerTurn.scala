package de.htwg.se.slay.controller

object StatePlayerTurn {
  var state = idle()

  def handle(e: PlayerTurn, c: Controller): Unit ={
    e match {
      case _:Player0Turn => state = player0(c)
      case _:Player1Turn => state = player1(c)
      case _:Player2Turn => state = player2(c)
    }
    state
  }

  def idle(): Unit = {}

  def player0(c: Controller): Unit = {
    c.moneymoney()
    c.nextturn()
  }
  def player1(c: Controller): Unit = c.turnPlayer(1)
  def player2(c: Controller): Unit = c.turnPlayer(2)

}
