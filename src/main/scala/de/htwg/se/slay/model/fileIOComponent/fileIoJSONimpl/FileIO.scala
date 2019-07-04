package de.htwg.se.slay.model.fileIOComponent.fileIoJSONimpl

import com.google.inject.{Guice, Injector}
import net.codingwell.scalaguice.InjectorExtensions._
import de.htwg.se.slay.SlayModule
import play.api.libs.json._
import de.htwg.se.slay.model.fileIOComponent.FileIOInterface
import de.htwg.se.slay.model.gamepieceComponent._
import de.htwg.se.slay.model.gridComponent.{FieldFactory, FieldInterface, GridFactory, GridInterface}
import de.htwg.se.slay.model.playerComponent.Player

import scala.io.Source

class FileIO extends FileIOInterface {
  val injector: Injector = Guice.createInjector(new SlayModule)

  override def load(name: String, players: Vector[Player]): (String, String, Int, GridInterface) = {
    val source: String = Source.fromFile("SaveFiles/" + name + ".json").getLines.mkString
    val json: JsValue = Json.parse(source)
    val p1name = (json \ "players" \ "p1").as[String]
    val p2name = (json \ "players" \ "p2").as[String]
    val state = (json \ "state" \ "state").get.toString.toInt

    var fields: Vector[FieldInterface] = Vector.empty
    val fieldsJson = (json \ "grid" \ "fields").asOpt[JsArray]
    fieldsJson match {
      case Some(fieldArray) =>
        for (field <- fieldArray.value) {
          val owner = players((field \ "owner").get.toString.toInt)
          val f = injector.instance[FieldFactory].create(owner)
          f.gamepiece = (field \ "gamepiece").as[String] match {
            case " " => NoPiece()
            case "G" => Grave()
            case "T" => Tree()
            case "B" => Castle(owner)
            case "C" => new Capital(owner)
            case "1" => new Peasant(owner)
            case "2" => new Spearman(owner)
            case "3" => new Knight(owner)
            case "4" => new Baron(owner)
            case _ => NoPiece()
          }
          fields = fields :+ f
        }
    }
    val rowIdx = (json \ "grid" \ "rowIdx").get.toString.toInt
    val colIdx = (json \ "grid" \ "colIdx").get.toString.toInt
    val grid = injector.instance[GridFactory].create(fields, rowIdx, colIdx)

    (p1name, p2name, state, grid)
  }

  override def save(name: String, players: Vector[Player], state: Int, grid: GridInterface): Unit = {
    val json = Json.obj(
      "players" -> playersToJson(players),
      "state" -> stateToJson(state),
      "grid" -> gridToJson(grid, players)
    )
    import java.io._
    val pw = new PrintWriter(new File("SaveFiles/" + name + ".json"))
    pw.write(Json.prettyPrint(json))
    pw.close()
  }

  def playersToJson(players: Vector[Player]): JsObject ={
    Json.obj("p1" -> players(1).name, "p2" -> players(2).name)
  }

  def stateToJson(state: Int): JsObject = {
    Json.obj("state" -> state)
  }

  def gridToJson(grid: GridInterface, players: Vector[Player]): JsObject ={
    Json.obj(
      "rowIdx" -> grid.rowIdx,
      "colIdx" -> grid.colIdx,
      "fields" -> Json.toJson(
        for(field <- grid) yield fieldToJson(field, players)
      )
    )
  }

  def fieldToJson(field: FieldInterface, players: Vector[Player]): JsObject ={
    Json.obj(
      "owner" -> (field.owner match{
        case p0 if p0 == players(0) => 0
        case p1 if p1 == players(1) => 1
        case p2 if p2 == players(2) => 2}),
      "gamepiece" -> field.gamepiece.toString
    )
  }
}
