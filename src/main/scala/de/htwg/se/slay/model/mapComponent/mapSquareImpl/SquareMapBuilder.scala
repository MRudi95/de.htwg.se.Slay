package de.htwg.se.slay.model.mapComponent.mapSquareImpl

import net.codingwell.scalaguice.InjectorExtensions._
import com.google.inject.{Guice, Injector}
import com.google.inject.assistedinject.Assisted
import de.htwg.se.slay.SlayModule
import de.htwg.se.slay.model.gamepieceComponent.{Capital, Tree}
import de.htwg.se.slay.model.gridComponent._
import de.htwg.se.slay.model.mapComponent.MapInterface
import de.htwg.se.slay.model.playerComponent.Player
import de.htwg.se.slay.util.MapBuilder
import javax.inject.Inject

import scala.collection.immutable.HashSet
import scala.io.BufferedSource

class SquareMapBuilder @Inject() (@Assisted val players:Vector[Player]) extends MapBuilder with MapInterface {

  val injector: Injector = Guice.createInjector(new SlayModule)

  override def gridCreator(mapname:String, typ:String = "main"):(GridInterface, HashSet[FieldInterface]) = {
    //val map: BufferedSource = io.Source.fromFile("src/" + typ + "/resources/maps/" + mapname + ".csv")
    val map: BufferedSource = io.Source.fromURL(this.getClass.getResource("/maps/" + mapname + ".csv"))
    val (grid, rowIdx) = readCSV(map)
    map.close

    val colIdx = grid.length / (rowIdx+1) - 1

    setNeighbors(grid, rowIdx, colIdx)

    (injector.instance[GridFactory].create(grid, rowIdx, colIdx), setTerritories(grid))
  }

  protected override def readCSV(map: BufferedSource): (Vector[FieldInterface], Int) = {
    var rowIdx = 0
    var grid:Vector[FieldInterface] = Vector.empty
    var idxC = 0
    for(line <- map.getLines()){
      val fields = line.split(";")
      for(field <- fields) {
        field match {
          case "0"  =>
            grid = grid :+ injector.instance[FieldFactory].create(players(0))
          case "10" =>
            grid = grid :+ injector.instance[FieldFactory].create(players(1))
          case "11" =>
            val f = injector.instance[FieldFactory].create(players(1))
            f.gamepiece = new Capital(players(1))
            grid = grid :+ f
          case "12" =>
            val f = injector.instance[FieldFactory].create(players(1))
            f.gamepiece = Tree()
            grid = grid :+ f
          case "20" =>
            grid = grid :+ injector.instance[FieldFactory].create(players(2))
          case "21" =>
            val f = injector.instance[FieldFactory].create(players(2))
            f.gamepiece = new Capital(players(2))
            grid = grid :+ f
          case "22" =>
            val f = injector.instance[FieldFactory].create(players(2))
            f.gamepiece = Tree()
            grid = grid :+ f
        }
        idxC += 1
      }
      rowIdx += 1
    }
    (grid, rowIdx-1)
  }

  override def setNeighbors(grid: Vector[FieldInterface], rowIdx: Int, colIdx: Int): Unit = {
    var neighborN:Option[FieldInterface] = None
    var neighborW:Option[FieldInterface] = None
    var neighborE:Option[FieldInterface] = None
    var neighborS:Option[FieldInterface] = None

    var idxF = 0
    for(f <- grid){
      val idxN = idxF - 1 - colIdx
      val idxW = idxF - 1
      val idxE = idxF + 1
      val idxS = idxF + 1 + colIdx

      if(idxN < 0) neighborN = None else neighborN = Option(grid(idxN))

      if((idxW+1) % (colIdx+1) == 0) neighborW = None else neighborW = Option(grid(idxW))

      if(idxE % (colIdx+1) == 0) neighborE = None else neighborE = Option(grid(idxE))

      if(idxS >= (rowIdx+1) * (colIdx+1)) neighborS = None else neighborS = Option(grid(idxS))

      f.setNeighbors(injector.instance[NeighborFactory].create(neighborN, neighborW, neighborE, neighborS))

      idxF += 1
    }
  }

  override def setTerritories(grid: Vector[FieldInterface]): HashSet[FieldInterface] = {
    var capitals: HashSet[FieldInterface] = HashSet()
    for(field <- grid){
      if(field.territory == null) {
        field.territory = injector.getInstance(classOf[TerritoryInterface])
        field.territory.addField(field)
      }

      field.gamepiece match {
        case _:Capital =>
          field.territory.setCapital(field)
          capitals += field
        case _ =>
      }

      field.neighbors.neighborEast match {
        case Some(east) if east.owner == field.owner && east.territory != null =>
          if(field.territory.size == 1) {
            field.territory = east.territory
            field.territory.addField(field)
          } else{
            east.territory.fields.find(_.gamepiece.isInstanceOf[Capital]) match{
              case Some(capField) => field.territory.setCapital(capField)
              case None =>
            }
            east.territory.fields.foreach { f =>
              f.territory = field.territory
              field.territory.addField(f)
            }
          }
        case Some(east) if east.owner == field.owner && east.territory == null =>
          east.territory = field.territory
          field.territory.addField(east)
        case _ =>
      }

      field.neighbors.neighborSouth match {
        case Some(south) if south.owner == field.owner =>
          field.territory.addField(south)
          south.territory = field.territory
        case _ =>
      }
    }

    capitals
  }
}
