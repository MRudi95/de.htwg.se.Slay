package de.htwg.se.slay.model.gridComponent.gridBaseImpl

import com.google.inject.assistedinject.Assisted
import de.htwg.se.slay.model.gridComponent.{FieldInterface, GridInterface}
import de.htwg.se.slay.model.playerComponent.Player

import javax.inject.Inject

case class Grid @Inject() (@Assisted private val grid:Vector[FieldInterface],
                           @Assisted("row") rowIdx:Int, @Assisted("col") colIdx:Int) extends GridInterface {
  override def length: Int = grid.length
  override def apply(idx: Int):FieldInterface = grid(idx)

  private val R = "\u001b[0m"       //Color Reset
  private val B = "\u001b[1;97m"    //Text Color Black

  private val p0Color = "\u001b[104m"
  private val p1Color = "\u001b[103m"
  private val p2Color = "\u001b[102m"

  private val abcLine = abcIndex

  def toString(players: Vector[Player]): String ={

    var StringGrid = "\n" + abcLine + "\n"
    for(rows <- 0 to rowIdx) {
      var lineOne, lineTwo, lineThree = ""

      for (cols <- 0 to colIdx) {

        val idx = rows * (colIdx+1) + cols
        val color = if(grid(idx).owner == players(0))
          p0Color
        else if(grid(idx).owner == players(1))
          p1Color
        else
          p2Color


        lineOne   += color + "     "
        lineTwo   += color + "  " + color + B + grid(idx).gamepiece.toString + "  "
        lineThree += color + "     "
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
