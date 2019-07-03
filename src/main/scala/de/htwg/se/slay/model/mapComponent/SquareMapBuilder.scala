package de.htwg.se.slay.model.mapComponent

import de.htwg.se.slay.model.gamepieceComponent.{Capital, Tree}
import de.htwg.se.slay.model.gridComponent.{Field, Grid, Neighbors, Territory}
import de.htwg.se.slay.model.playerComponent.Player
import de.htwg.se.slay.util.MapBuilder

import scala.collection.immutable.HashSet
import scala.io.BufferedSource

class SquareMapBuilder(val players:Vector[Player]) extends MapBuilder {

  override def gridCreator(mapname:String, typ:String = "main"):(Grid, HashSet[Field]) = {
    val map: BufferedSource = io.Source.fromFile("src/" + typ + "/resources/maps/" + mapname + ".csv")
    val (grid, rowIdx) = readCSV(map)
    map.close

    val colIdx = grid.length / (rowIdx+1) - 1

    setNeighbors(grid, rowIdx, colIdx)

    (Grid(grid, rowIdx, colIdx), setTerritories(grid))
  }

  protected override def readCSV(map: BufferedSource): (Vector[Field], Int) = {
    var rowIdx = 0
    var grid:Vector[Field] = Vector.empty
    var idxC = 0
    for(line <- map.getLines()){
      val fields = line.split(";")
      for(field <- fields) {
        field match {
          case "0"  => grid = grid :+ new Field(players(0))
          case "10" => grid = grid :+ new Field(players(1))
          case "11" => grid = grid :+ new Field(players(1), new Capital(players(1)))
          case "12" => grid = grid :+ new Field(players(1), Tree())
          case "20" => grid = grid :+ new Field(players(2))
          case "21" => grid = grid :+ new Field(players(2), new Capital(players(2)))
          case "22" => grid = grid :+ new Field(players(2), Tree())
        }
        idxC += 1
      }
      rowIdx += 1
    }
    (grid, rowIdx-1)
  }

  protected override def setNeighbors(grid: Vector[Field], rowIdx: Int, colIdx: Int): Unit = {
    var neighborN:Field = null
    var neighborW:Field = null
    var neighborE:Field = null
    var neighborS:Field = null

    var idxF = 0
    for(f <- grid){
      val idxN = idxF - 1 - colIdx
      val idxW = idxF - 1
      val idxE = idxF + 1
      val idxS = idxF + 1 + colIdx

      if(idxN < 0) neighborN = null else neighborN = grid(idxN)

      if((idxW+1) % (colIdx+1) == 0) neighborW = null else neighborW = grid(idxW)

      if(idxE % (colIdx+1) == 0) neighborE = null else neighborE = grid(idxE)

      if(idxS >= (rowIdx+1) * (colIdx+1)) neighborS = null else neighborS = grid(idxS)

      f.setNeighbors(Neighbors(neighborN, neighborW, neighborE, neighborS))

      idxF += 1
    }
  }

  protected override def setTerritories(grid: Vector[Field]): HashSet[Field] = {
    var capitals: HashSet[Field] = HashSet()
    for(field <- grid){
      if(field.territory == null) {
        field.territory = new Territory
        field.territory.addField(field)
      }

      field.gamepiece match {
        case _:Capital =>
          field.territory.setCapital(field)
          capitals += field
        case _ =>
      }

      val east = field.neighbors.neighborEast
      val south = field.neighbors.neighborSouth
      if(east != null && east.owner == field.owner) {
        if(east.territory != null) {
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
        } else {
          east.territory = field.territory
          field.territory.addField(east)
        }
      }
      if(south != null && south.owner == field.owner) {
        field.territory.addField(south)
        south.territory = field.territory
      }
    }

    capitals
  }
}
