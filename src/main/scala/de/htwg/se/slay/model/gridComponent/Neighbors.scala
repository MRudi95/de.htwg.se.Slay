package de.htwg.se.slay.model.gridComponent

case class Neighbors(neighborNorth:Field, neighborWest:Field,
                     neighborEast:Field, neighborSouth:Field) extends Iterable[Field]{

  override def iterator: Iterator[Field] = Iterator[Field](neighborNorth, neighborWest, neighborEast, neighborSouth)
}
