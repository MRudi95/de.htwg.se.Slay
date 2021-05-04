package de.htwg.se.slay.model.playerComponent

import slick.jdbc.PostgresProfile.api._

class Players(tag: Tag) extends Table[(Option[Int], String, Int, Int)](tag, "players") {

  def id = column[Int]("id", O.PrimaryKey, O.AutoInc, O.SqlType("BIGSERIAL"))

  def name = column[String]("name")

  def wins = column[Int]("wins")

  def losses = column[Int]("losses")

  // Every table needs a * projection with the same type as the table's type parameter
  def *  = (id.?, name, wins, losses)
}



