package de.htwg.se.slay.model.gridComponent

import com.google.inject.assistedinject.Assisted


trait NeighborInterface extends Iterable[Option[FieldInterface]] {
  def neighborNorth: Option[FieldInterface]

  def neighborWest: Option[FieldInterface]

  def neighborEast: Option[FieldInterface]

  def neighborSouth: Option[FieldInterface]

  override def iterator: Iterator[Option[FieldInterface]] = {
    Iterator[Option[FieldInterface]](neighborNorth, neighborWest, neighborEast, neighborSouth)
  }
}

trait NeighborFactory{
  def create(@Assisted("north") neighborNorth:Option[FieldInterface],
             @Assisted("west") neighborWest:Option[FieldInterface],
             @Assisted("east") neighborEast:Option[FieldInterface],
             @Assisted("south") neighborSouth:Option[FieldInterface]): NeighborInterface
}