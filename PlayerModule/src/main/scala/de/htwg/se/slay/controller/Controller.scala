package de.htwg.se.slay.controller

import com.google.inject.{Guice, Injector}
import de.htwg.se.slay.model.playerComponent.Player
import de.htwg.se.slay.{PlayerMod, PlayerModule}
import play.api.libs.json.{JsObject, JsValue, Json}

class Controller {
  val injector: Injector = Guice.createInjector(new PlayerModule)

  var player: Player = Player()

  def getPlayer(id: String): String = {
    "test" + id
  }

  def changePlayerName(id: String, json: JsValue): String = {
    val newname = (json \ "name").as[String]

    player = player.changeName(newname)

    Json.obj(
      "success" -> true,
      "id" -> player.id,
      "name" -> player.name
    ).toString()
  }

  def updateResult(id: String, json: JsValue): String = {
    val result = (json \ "result").as[Boolean]

    player = player.playerWonGame(result)

    Json.obj(
      "success" -> true,
      "id" -> player.id,
      "name" -> player.name,
      "wins" -> player.wins,
      "losses" -> player.losses,
      "w/l" -> player.winLossRatio()
    ).toString()
  }
}
