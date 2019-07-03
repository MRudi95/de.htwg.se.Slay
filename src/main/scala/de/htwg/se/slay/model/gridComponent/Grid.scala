package de.htwg.se.slay.model.gridComponent

case class Grid(private val grid:Vector[FieldInterface], rowIdx:Int, colIdx:Int) extends GridInterface {
  override def length: Int = grid.length
  override def apply(idx: Int):FieldInterface = grid(idx)

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
        lineOne   += grid(idx).owner.color + "     "
        lineTwo   += grid(idx).owner.color + "  " + grid(idx).owner.color + B + grid(idx).gamepiece.toString + "  "
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
