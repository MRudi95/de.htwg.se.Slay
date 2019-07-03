package de.htwg.se.slay.model.gridComponent

case class Neighbors(neighborNorth:FieldInterface, neighborWest:FieldInterface,
                     neighborEast:FieldInterface, neighborSouth:FieldInterface) extends NeighborInterface {

 // override def iterator: Iterator[Field] = Iterator[Field](neighborNorth, neighborWest, neighborEast, neighborSouth)
}
