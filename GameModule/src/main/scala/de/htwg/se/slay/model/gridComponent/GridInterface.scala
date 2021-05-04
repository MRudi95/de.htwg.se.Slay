package de.htwg.se.slay.model.gridComponent

import com.google.inject.assistedinject.Assisted
import de.htwg.se.slay.model.playerComponent.Player

trait GridInterface extends IndexedSeq[FieldInterface]{
  override def length: Int
  override def apply(idx: Int):FieldInterface

  def toString(players: Vector[Player]): String

  def rowIdx: Int
  def colIdx: Int
}

trait GridFactory{
  def create(grid:Vector[FieldInterface], @Assisted("row") rowIdx:Int, @Assisted("col") colIdx:Int): GridInterface
}