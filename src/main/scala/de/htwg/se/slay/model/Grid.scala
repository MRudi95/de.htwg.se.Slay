package de.htwg.se.slay.model

case class Grid(private val grid:Vector[Field], rowIdx:Int, colIdx:Int) extends IndexedSeq[Field]{
  override def length: Int = grid.length
  override def apply(idx: Int):Field = grid(idx)

  private val R = "\033[0m"       //Color Reset
  private val B = "\033[1;97m"    //Text Color Black

  private val abcLine = abcIndex
  setNeighbors()

  override def toString: String ={
    var StringGrid = "\n" + abcLine + "\n"
    for(rows <- 0 to rowIdx) {
      var lineOne = ""
      var lineTwo = ""
      var lineThree = ""

      for (cols <- 0 to colIdx) {
        val idx = rows * (colIdx+1) + cols
        val gamePiece = grid(idx).gamepiece.toString
        lineOne   += grid(idx).owner.color + "     "
        lineTwo   += grid(idx).owner.color + "  " + grid(idx).owner.color + B + gamePiece + "  "
        lineThree += grid(idx).owner.color + "     "
      }

      StringGrid += "  " + lineOne + R + "\n"
      StringGrid += rows+1 + " " + lineTwo + R + "\n"
      StringGrid += "  " + lineThree + R + "\n"
    }
    StringGrid
  }

  private def abcIndex: String ={
    var char:Int = 65 // char 'A'
    var abc = "  "
    for(_ <- 0 to colIdx){
      abc += "  " + char.toChar + "  "
      char += 1
    }
    abc
  }

  private def setNeighbors(): Unit = {
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
