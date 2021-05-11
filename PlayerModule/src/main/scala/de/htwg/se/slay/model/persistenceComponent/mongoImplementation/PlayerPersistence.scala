package de.htwg.se.slay.model.persistenceComponent.mongoImplementation

import de.htwg.se.slay.model.persistenceComponent.PlayerPersistenceInterface
import de.htwg.se.slay.model.playerComponent.Player
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.bson.codecs.configuration.CodecRegistry
import org.mongodb.scala.{MongoClient, MongoCollection, MongoDatabase}
import org.mongodb.scala.bson.codecs.Macros._
import org.mongodb.scala._
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.bson.types.ObjectId
import org.mongodb.scala.bson.codecs.Macros
import org.mongodb.scala.model.Updates._
import org.mongodb.scala.model.Filters._

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class PlayerPersistence extends PlayerPersistenceInterface{
  private val databaseString = sys.env.getOrElse("DATABASE_PLAYER", "mongodb://root:123@localhost/playermodule?retryWrites=true&w=majority")
  val uri: String = databaseString

  val client: MongoClient = MongoClient(uri)
  val codecRegistry: CodecRegistry = fromRegistries(fromProviders(Macros.createCodecProviderIgnoreNone[Player]), MongoClient.DEFAULT_CODEC_REGISTRY)
  val db: MongoDatabase = client.getDatabase("playermodule").withCodecRegistry(codecRegistry)
  val players: MongoCollection[Player] = db.getCollection("players")

  override def create(player: Player): Player = {
    val playerWithID = player.copy(id = Some(ObjectId.get().toString))
    players.insertOne(playerWithID).toFuture()
    playerWithID
  }

  override def read(playerId: String): Option[Player] = {
    Await.result(players.find(equal("_id", playerId)).first().toFutureOption(), Duration("10s"))
  }

  override def update(player: Player): Unit = {
    val updatedValues = Seq(
      set("name", player.name),
      set("wins", player.wins),
      set("losses", player.losses),
    )

    players.updateOne(equal("_id", player.id.getOrElse(throw new RuntimeException("Can not update element with faulty id"))), updatedValues)
  }

  override def delete(player: Player): Unit = {
    players.deleteOne(equal("_id", player.id.get)).toFuture()
  }
}
