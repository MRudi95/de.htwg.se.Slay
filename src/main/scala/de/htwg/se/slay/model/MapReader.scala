package de.htwg.se.slay.model

import scala.collection.immutable.HashSet
import scala.io.BufferedSource

class MapReader(val players:Vector[Player]) {

  def gridCreator(mapname:String, typ:String = "main"):(Grid, HashSet[Field]) = {
    val map: BufferedSource = io.Source.fromFile("src/" + typ + "/resources/maps/" + mapname + ".csv")
    val (grid, rowIdx) = readCSV(map)
    map.close

    val colIdx = grid.length / (rowIdx+1) - 1

    setNeighbors(grid, rowIdx, colIdx)

    (Grid(grid, rowIdx, colIdx), setTerritories(grid))
  }

  private def readCSV(map: BufferedSource): (Vector[Field], Int) = {
    var rowIdx = 0
    var grid:Vector[Field] = Vector.empty
    var idxC = 0
    for(line <- map.getLines()){
      val fields = line.split(";")
      for(field <- fields) {
        field match {
          case "0"  => grid = grid :+ new Field(players(0))
          case "10" => grid = grid :+ new Field(players(1))
          case "11" => grid = grid :+ new Field(players(1), new Capital(players(1), idxC))
          case "12" => grid = grid :+ new Field(players(1), Tree())
          case "20" => grid = grid :+ new Field(players(2))
          case "21" => grid = grid :+ new Field(players(2), new Capital(players(2), idxC))
          case "22" => grid = grid :+ new Field(players(2), Tree())
        }
        idxC += 1
      }
      rowIdx += 1
    }
    (grid, rowIdx-1)
  }

  private def setNeighbors(grid: Vector[Field], rowIdx: Int, colIdx: Int): Unit = {
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

  private def setTerritories(grid: Vector[Field]): HashSet[Field] = {
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
          field.territory = east.territory
          field.territory.addField(field)
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
