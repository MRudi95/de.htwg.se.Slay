package de.htwg.se.slay.model

import scala.io.BufferedSource

class MapReader(val players:Vector[Player]) {

  def gridCreator(mapname:String):Grid = {
    val map: BufferedSource = io.Source.fromFile("src/main/scala/de/htwg/se/slay/model/" + mapname + ".csv")
    val (grid, rowIdx) = readCSV(map)
    map.close

    val colIdx = grid.length / (rowIdx+1) - 1

    setNeighbors(grid, rowIdx, colIdx)
    Grid(grid, rowIdx, colIdx)
  }

  private def readCSV(map: BufferedSource): (Vector[Field], Int) = {
    var rowIdx = 0
    var grid:Vector[Field] = Vector.empty
    for(line <- map.getLines()){
      val fields = line.split(";")
      for(field <- fields) {
        field match {
          case "0"  =>
            grid = grid :+ new Field(players(0))
          case "10" =>
            grid = grid :+ new Field(players(1))
          case "11" =>
            grid = grid :+ new Field(players(1), new Capital(players(1)))
          case "12" =>
            grid = grid :+ new Field(players(1), Tree())
          case "20" =>
            grid = grid :+ new Field(players(2))
          case "21" =>
            grid = grid :+ new Field(players(2), new Capital(players(2)))
          case "22" =>
            grid = grid :+ new Field(players(2), Tree())
        }
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

      if(idxS > rowIdx * colIdx) neighborS = null else neighborS = grid(idxS)

      f.setNeighbors(Neighbors(neighborN, neighborW, neighborE, neighborS))

      idxF += 1
    }
  }
}
