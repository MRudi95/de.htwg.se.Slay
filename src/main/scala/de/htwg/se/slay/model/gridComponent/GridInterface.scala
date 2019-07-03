package de.htwg.se.slay.model.gridComponent

import com.google.inject.assistedinject.Assisted

trait GridInterface extends IndexedSeq[FieldInterface]{
  override def length: Int
  override def apply(idx: Int):FieldInterface

  override def toString: String

  def rowIdx: Int
  def colIdx: Int
}

trait GridFactory{
  def create(grid:Vector[FieldInterface], @Assisted("row") rowIdx:Int, @Assisted("col") colIdx:Int): GridInterface
}