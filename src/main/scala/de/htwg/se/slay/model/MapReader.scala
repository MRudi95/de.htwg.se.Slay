package de.htwg.se.slay.model

object MapReader {
  def readMap(mapname:String, players:Vector[Player]):Grid = {
    val map = io.Source.fromFile("src/main/scala/de/htwg/se/slay/model/" + mapname + ".csv")
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
    map.close

    val colIdx = grid.length / rowIdx - 1
    rowIdx -= 1

    var idxF = 0
    for(f <- grid){
      val idxN = idxF - 1 - colIdx
      val idxW = idxF - 1
      val idxE = idxF + 1
      val idxS = idxF + 1 + colIdx
      var neighborN:Field = null
      var neighborW:Field = null
      var neighborE:Field = null
      var neighborS:Field = null

      if(idxN < 0) neighborN = null else neighborN = grid(idxN)

      if((idxW+1) % (colIdx+1) == 0) neighborW = null else neighborW = grid(idxW)

      if(idxE % (colIdx+1) == 0) neighborE = null else neighborE = grid(idxE)

      if(idxS > rowIdx * colIdx) neighborS = null else neighborS = grid(idxS)

      f.setNeighbors(Neighbors(neighborN, neighborW, neighborE, neighborS))

      idxF += 1
    }

    Grid(grid, rowIdx, colIdx)
  }
}
