package de.htwg.se.slay.model.fileIOComponent.fileIoXMLimpl

import com.google.inject.{Guice, Injector}
import de.htwg.se.slay.SlayModule
import de.htwg.se.slay.controller.controllerComponent.ControllerInterface
import de.htwg.se.slay.model.fileIOComponent.FileIOInterface
import de.htwg.se.slay.model.gridComponent.{FieldFactory, FieldInterface, GridInterface}
import de.htwg.se.slay.model.playerComponent.Player

import scala.xml.{Elem, NodeBuffer}
import net.codingwell.scalaguice.InjectorExtensions._

class FileIO extends FileIOInterface{

  val injector: Injector = Guice.createInjector(new SlayModule)
  override def load(name: String, players: Vector[Player], ctrl: ControllerInterface): (String, String, Int, GridInterface) = {
    val file = scala.xml.XML.loadFile("SaveFiles/" + name + ".xml")
    val p1name = (file \ "p1").text
    val p2name = (file \ "p2").text
    val state = (file \ "state").text.toInt

    val fieldNodes = file \\ "field"
    var fields: Vector[FieldInterface] = Vector.empty
    for(field <- fieldNodes){
      val f = injector.instance[FieldFactory].create(players((field \ "@owner").text.toInt))
      f.gamepiece = (field \ "@gamepiece").text match{
        case
      }
      fields = fields :+
    }
  }

  override def save(name: String, players: Vector[Player], state: Int, grid: GridInterface): Unit = {
    val xml = <xml>{playersToXML(players)}{stateToXML(state)}{gridToXML(grid, players)}</xml>
    import java.io._
    val pw = new PrintWriter(new File("SaveFiles/" + name + ".xml"))
    pw.write(xml.toString())
    pw.close()
  }

  def playersToXML(players: Vector[Player]): NodeBuffer={
    <p1>{players(1).name}</p1>
    <p2>{players(2).name}</p2>
  }

  def stateToXML(state: Int): Elem ={
    <state>{state.toString}</state>
  }

  def gridToXML(grid: GridInterface, players: Vector[Player]): Elem ={
    <grid rowIdx={grid.rowIdx.toString} colIdx={grid.colIdx.toString}>
      {for(field <- grid) yield fieldToXML(field, players)}
    </grid>
  }

  def fieldToXML(field: FieldInterface, players: Vector[Player]): Elem ={
    <field owner={ field.owner match{
                    case p0 if p0 == players(0) => "0"
                    case p1 if p1 == players(1) => "1"
                    case p2 if p2 == players(2) => "2"}}
           gamepiece={field.gamepiece.toString}>
    </field>
  }
}
