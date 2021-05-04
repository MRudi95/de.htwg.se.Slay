package de.htwg.se.slay

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.PathMatchers
import de.htwg.se.slay.controller.Controller
import play.api.libs.json.Json

import scala.concurrent.ExecutionContextExecutor
import scala.io.StdIn

object PlayerMod{
  val connectionInterface = "0.0.0.0"
  val connectionPort: Int = sys.env.getOrElse("PORT", 9002).toString.toInt

  def main(args: Array[String]) : Unit = {
    val controller = new Controller()

    implicit val actorSystem: ActorSystem = ActorSystem("actorSystem")
    implicit val executionContext: ExecutionContextExecutor = actorSystem.dispatcher

    val route = concat(
      pathPrefix("player" / PathMatchers.Segment) { id =>
        concat(
          get {
            complete(HttpEntity(ContentTypes.`application/json`, controller.getPlayer(id)))
          },
          put {
            entity(as[String]) { jsonString => {
              val json = Json.parse(jsonString)
              complete(HttpEntity(ContentTypes.`application/json`, controller.changePlayerName(id, json)))
            }
            }
          },

        )
      },
      pathPrefix("player") {
        concat(
          post {
            entity(as[String]) { jsonString => {
              val json = Json.parse(jsonString)
              complete(HttpEntity(ContentTypes.`application/json`, controller.createPlayer(json)))
            }
            }
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
  }
}