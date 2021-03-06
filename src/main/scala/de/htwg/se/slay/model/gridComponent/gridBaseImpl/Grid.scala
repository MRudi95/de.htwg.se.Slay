package de.htwg.se.slay.model.gridComponent.gridBaseImpl

import com.google.inject.assistedinject.Assisted
import de.htwg.se.slay.model.gridComponent.{FieldInterface, GridInterface}
import javax.inject.Inject

case class Grid @Inject() (@Assisted private val grid:Vector[FieldInterface],
                           @Assisted("row") rowIdx:Int, @Assisted("col") colIdx:Int) extends GridInterface {
  override def length: Int = grid.length
  override def apply(idx: Int):FieldInterface = grid(idx)

  private val R = "\u001b[0m"       //Color Reset
  private val B = "\u001b[1;97m"    //Text Color Black

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
