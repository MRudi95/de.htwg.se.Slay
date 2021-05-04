package de.htwg.se.slay.model.gridComponent.gridBaseImpl

import com.google.inject.assistedinject.Assisted
import de.htwg.se.slay.model.gridComponent.{FieldInterface, NeighborInterface}
import javax.inject.Inject

case class Neighbors @Inject() (@Assisted("north") neighborNorth: Option[FieldInterface],
                                @Assisted("west") neighborWest: Option[FieldInterface],
                                @Assisted("east") neighborEast: Option[FieldInterface],
                                @Assisted("south") neighborSouth: Option[FieldInterface]) extends NeighborInterface{

}
