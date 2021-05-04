package de.htwg.se.slay.model.playerComponent

case class Player (id: Option[String] = None,
                   name: String = "",
                   wins: Int = 0,
                   losses: Int = 0) {

  override def toString: String = name

  def changeName(newName: String): Player = {
    copy(name = newName)
  }

  def playerWonGame(won: Boolean): Player = {
    if (won)
      copy(wins = wins+1)
    else
      copy(losses = losses+1)
  }

  def winLossRatio(): Double = {
    wins / losses
  }
}