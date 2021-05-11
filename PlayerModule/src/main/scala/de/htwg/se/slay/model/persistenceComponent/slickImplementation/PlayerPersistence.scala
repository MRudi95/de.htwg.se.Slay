package de.htwg.se.slay.model.persistenceComponent.slickImplementation

import de.htwg.se.slay.model.persistenceComponent.PlayerPersistenceInterface
import de.htwg.se.slay.model.playerComponent.{Player, Players}
import slick.jdbc.JdbcBackend.Database
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class PlayerPersistence extends PlayerPersistenceInterface{
  val db = Database.forURL(
    "jdbc:postgresql://slick-db:5432/playermodule",
    "root", "123",
    null,
    "org.postgresql.Driver")


  val players = TableQuery[Players]

  val setup = DBIO.seq(
    players.schema.createIfNotExists
  )
  db.run(setup)

  override def create(player: Player): Player = {
    val playerIdQuery = (players returning players.map(_.id)) += (None, player.name, player.wins, player.losses)
    val playerId = Await.result(db.run(playerIdQuery), Duration("10s"))
    player.copy(id = Some(playerId.toString))
  }

  override def read(playerId: String): Option[Player] = {
    val query = players.filter(_.id === playerId.toInt)
    val playerOption = Await.result(db.run(query.result.headOption), Duration("10s"))
    playerOption match {
      case Some(players) => Some(Player(Some(players._1.get.toString), players._2, players._3, players._4))
      case None => None
    }
  }

  override def update(player: Player): Unit = {
    val query = players.filter(_.id === player.id.get.toInt)
    val update = query.update(Some(player.id.get.toInt), player.name, player.wins, player.losses)
    db.run(update)
  }

  override def delete(player: Player): Unit = {
    val query = players.filter(_.id === player.id.get.toInt).delete
    db.run(query)
  }
}
