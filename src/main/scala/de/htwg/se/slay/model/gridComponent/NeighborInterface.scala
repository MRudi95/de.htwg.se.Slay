package de.htwg.se.slay.model.gridComponent


trait NeighborInterface extends Iterable[FieldInterface] {
  def neighborNorth: FieldInterface

  def neighborWest: FieldInterface

  def neighborEast: FieldInterface

  def neighborSouth: FieldInterface

  override def iterator: Iterator[FieldInterface] = {
    Iterator[FieldInterface](neighborNorth, neighborWest, neighborEast, neighborSouth)
  }
}

trait NeighborFactory{
  def create(): NeighborInterface
}