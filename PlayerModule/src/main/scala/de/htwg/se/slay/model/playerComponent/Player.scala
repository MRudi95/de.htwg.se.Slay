package de.htwg.se.slay.model.playerComponent

import org.mongodb.scala.bson.annotations.BsonProperty

case class Player (@BsonProperty("_id") id: Option[String] = None,
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
    if(losses == 0)
      return wins

    wins / losses
  }
}