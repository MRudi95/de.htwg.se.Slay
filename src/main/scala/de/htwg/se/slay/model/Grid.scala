package de.htwg.se.slay.model

case class Grid(private val grid:Vector[Field], rowIdx:Int, colIdx:Int) extends IndexedSeq[Field]{
  override def length: Int = grid.length
  override def apply(idx: Int):Field = grid(idx)

  private val R = "\033[0m"       //Color Reset
  private val B = "\033[1;97m"    //Text Color Black

  private val abcLine = abcIndex

  override def toString: String ={
    var StringGrid = "\n" + abcLine + "\n"

    var lineOne = ""
    var lineTwo = ""
    var lineThree = ""

    for(rows <- 0 to rowIdx) {
      for (cols <- 0 to colIdx) {
        val idx = rows * (colIdx+1) + cols
        val gamePiece = getGamePiece(grid(idx))
        lineOne   += grid(idx).owner.color + "     "
        lineTwo   += grid(idx).owner.color + "  " + grid(idx).owner.color + B + gamePiece + "  "
        lineThree += grid(idx).owner.color + "     "
      }
      StringGrid += "  " + lineOne + R + "\n"
      StringGrid += rows+1 + " " + lineTwo + R + "\n"
      StringGrid += "  " + lineThree + R + "\n"

      lineOne = ""
      lineTwo = ""
      lineThree = ""
    }
    StringGrid
  }

  private def getGamePiece(field:Field):String = {
    field.gamepiece match{
      case _:Tree     => "T"
      case _:Castle   => "B"
      case _:Capital  => "C"
      case _:Grave    => "G"
      case _:Peasant  => "1"
      case _:Spearman => "2"
      case _:Knight   => "3"
      case _:Baron    => "4"
      case null       => " "
    }
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
