package de.htwg.se.slay.model

import scala.io.BufferedSource

class MapReader(val players:Vector[Player]) {

  def gridCreator(mapname:String):Grid = {
    val map: BufferedSource = io.Source.fromFile("src/main/scala/de/htwg/se/slay/model/" + mapname + ".csv")
    val (grid, rowIdx) = readCSV(map)
    map.close

    val colIdx = grid.length / (rowIdx+1) - 1

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
}
