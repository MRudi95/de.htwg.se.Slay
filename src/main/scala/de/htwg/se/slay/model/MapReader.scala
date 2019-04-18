package de.htwg.se.slay.model

class MapReader(players:Vector[Player]) {
  def readMap(mapname:String):Grid = {
    val map = io.Source.fromFile("src/main/scala/de/htwg/se/slay/model/" + mapname + ".csv")
    var rowIdx = 0
    var grid:Vector[Field] = Vector.empty
    for(line <- map.getLines()){
      val fields = line.split(";")
      val testfield = new Field(players(0))
      for(field <- fields) {
        field match {
          case "0"  =>
            grid = grid :+ testfield
          case "10" =>
            grid = grid :+ new Field(players(1))
          case "11" =>
            val tmp = new Field(players(1))
            tmp.gamepiece = new Capital(players(1))
            grid = grid :+ tmp
          case "12" =>
            val tmp = new Field(players(1))
            tmp.gamepiece = Tree()
            grid = grid :+ tmp
          case "20" =>
            grid = grid :+ new Field(players(2))
          case "21" =>
            val tmp = new Field(players(2))
            tmp.gamepiece = new Capital(players(2))
            grid = grid :+ tmp
          case "22" =>
            val tmp = new Field(players(2))
            tmp.gamepiece = Tree()
            grid = grid :+ tmp
        }
      }
      rowIdx += 1
    }
    val columnIdx = grid.length / rowIdx
    map.close
    Grid(grid, rowIdx-1, columnIdx-1)
  }
}
