package de.htwg.se.slay.aview

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives.{as, complete, concat, entity, get, path, pathPrefix, post, put}
import de.htwg.se.slay.controller.controllerComponent.ControllerInterface
import de.htwg.se.slay.model.fileIOComponent.fileIoJSONimpl.FileIO
import play.api.libs.json.{JsValue, Json}

import scala.concurrent.ExecutionContextExecutor
import scala.io.StdIn

class RestAPI(controller: ControllerInterface) {
  val connectionInterface = "0.0.0.0"
  val connectionPort: Int = sys.env.getOrElse("PORT", 9001).toString.toInt

  val jsonIO = new FileIO

  def run(): Unit = {
    implicit val actorSystem: ActorSystem = ActorSystem("actorSystem")
    implicit val executionContext: ExecutionContextExecutor = actorSystem.dispatcher

    val route  = concat(
      pathPrefix("game") {
        concat(
          path("command"){
            put(
              entity(as[String]){ jsonString => {
                val json = Json.parse(jsonString)
                complete(HttpEntity(ContentTypes.`application/json`, command(json)))
              }
              }
            )
          }
        )
      },
      pathPrefix("player"){
        concat(
          path("name"){
            put(
              entity(as[String]){ jsonString => {
                val json = Json.parse(jsonString)
                complete(HttpEntity(ContentTypes.`application/json`, controller.changeName((json \ "name").as[String])))
              }
              }
            )
          }
        )
      }
    )

    val bindingFuture = Http().bindAndHandle(route, connectionInterface, connectionPort)

    println(s"Server online at http://$connectionInterface:$connectionPort/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => actorSystem.terminate()) // and shutdown when done
    println("Server ded")
  }

  private def command(json: JsValue): String = {
    val command = (json \ "command").as[String]
    val coord1 = (json \ "coord1").as[String]
    val coord2 = (json \ "coord2").as[String]

    command match {
//      case "q" =>
//      case "quit" =>
      case "undo" => controller.undo()
      case "redo" => controller.redo()
      case "end" => controller.nextturn()
//      case save(file) => controller.save(file)
//      case load(file) => controller.load(file)
      case "ff20" => controller.surrender()
      case "bal" if checkIndex(coord1) =>
        controller.seeBalance(convertIndex(coord1))
      case "buy" if checkIndex(coord1) =>
        controller.buyPeasant(convertIndex(coord1))
      case "plc" if checkIndex(coord1) =>
        controller.placeCastle(convertIndex(coord1))
      case "mov" if checkIndex(coord1) && checkIndex(coord2) =>
        controller.moveUnit(convertIndex(coord1), convertIndex(coord2))
      case "cmb" if checkIndex(coord1) && checkIndex(coord2) =>
        controller.combineUnit(convertIndex(coord1), convertIndex(coord2))
      case _ => return Json.obj(
        "msg" -> "wrong input",
      ).toString()
    }

    Json.obj(
      "players" -> jsonIO.playersToJson(controller.players),
      "state" -> jsonIO.stateToJson(controller.state),
      "grid" -> jsonIO.gridToJson(controller.grid, controller.players)
    ).toString()
  }

  private def checkIndex(idx: String): Boolean ={
    val coord = "([A-Z])(\\d+)".r
    try{
      val coord(cols, rows) = idx
      val col = cols.charAt(0).toInt - 65
      if(col > controller.grid.colIdx || rows.toInt - 1 > controller.grid.rowIdx) false else true
    } catch {
      case e: MatchError => false
    }
  }

  private def convertIndex(idx: String): Int ={
    val cols = idx.charAt(0).toInt - 65
    val rows = idx.charAt(1).asDigit - 1
    rows * (controller.grid.colIdx+1) + cols
  }
}
