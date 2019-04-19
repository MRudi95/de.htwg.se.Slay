package de.htwg.se.slay.model

case class Grid(private val grid:Vector[Field], rowIdx:Int, colIdx:Int) extends IndexedSeq[Field]{
  override def length: Int = grid.length
  override def apply(idx: Int):Field = grid(idx)
}
