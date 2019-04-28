package de.htwg.se.slay.model

case class Grid(private val grid:Vector[Field], rowIdx:Int, colIdx:Int) extends IndexedSeq[Field]{
  override def length: Int = grid.length
  override def apply(idx: Int):Field = grid(idx)

  private val R = "\033[0m"       //Color Reset
  private val B = "\033[1;97m"    //Text Color Black

  private val abcLine = abcIndex

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
}
