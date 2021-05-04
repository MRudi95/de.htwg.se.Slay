package de.htwg.se.slay.controller

import com.google.inject.{Guice, Injector}
import de.htwg.se.slay.model.persistenceComponent.PlayerPersistenceInterface
import de.htwg.se.slay.model.playerComponent.Player
import de.htwg.se.slay.{PlayerMod, PlayerModule}
import play.api.libs.json.{JsObject, JsValue, Json}

class Controller {
  val injector: Injector = Guice.createInjector(new PlayerModule)
  val playerPersistence = injector.getInstance(classOf[PlayerPersistenceInterface])

  private def playerJson(player: Player): JsObject = {
    Json.obj(
      "success" -> true,
      "id" -> player.id,
      "name" -> player.name,
      "wins" -> player.wins,
      "losses" -> player.losses,
      "w/l" -> player.winLossRatio()
    )
  }

  def createPlayer(json: JsValue): String = {
    val name = (json \ "name").as[String]
    val player = playerPersistence.create(Player(name = name))
    playerJson(player).toString()
  }

  def getPlayer(id: String): String = {
    val playerOption = playerPersistence.read(id.toInt)
    playerOption match {
      case Some(player) =>
        playerJson(player).toString()
      case None => Json.obj(
        "success" -> false,
        "msg" -> s"Player with $id could not be found"
      ).toString()
    }
  }

  def changePlayerName(id: String, json: JsValue): String = {
    val newname = (json \ "name").as[String]

    val playerOption = playerPersistence.read(id.toInt)
    playerOption match {
      case Some(player) =>
        playerPersistence.update(player.changeName(newname))
        playerJson(player).toString()
      case None => Json.obj(
        "success" -> false,
        "msg" -> s"Player with $id could not be found"
      ).toString()
    }
  }

  def updateResult(id: String, json: JsValue): String = {
    val result = (json \ "result").as[Boolean]

    val playerOption = playerPersistence.read(id.toInt)
    playerOption match {
      case Some(player) =>
        playerPersistence.update(player.playerWonGame(result))
        playerJson(player).toString()
      case None => Json.obj(
        "success" -> false,
        "msg" -> s"Player with $id could not be found"
      ).toString()
    }
  }
}
