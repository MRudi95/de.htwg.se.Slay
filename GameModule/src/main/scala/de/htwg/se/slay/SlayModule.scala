package de.htwg.se.slay

import com.google.inject.AbstractModule
import com.google.inject.assistedinject.FactoryModuleBuilder
import de.htwg.se.slay.controller.controllerComponent.{ControllerInterface, controllerBaseImpl}
import de.htwg.se.slay.model.fileIOComponent._
import de.htwg.se.slay.model.gridComponent._
import de.htwg.se.slay.model.mapComponent.{MapFactory, MapInterface}
import de.htwg.se.slay.model.mapComponent.mapSquareImpl.SquareMapBuilder
import net.codingwell.scalaguice._

class SlayModule extends AbstractModule with ScalaModule{
  override def configure(): Unit = {
    bind[ControllerInterface].to[controllerBaseImpl.Controller]

    install(new FactoryModuleBuilder()
      .implement(classOf[MapInterface], classOf[SquareMapBuilder])
      .build(classOf[MapFactory])
    )

    install(new FactoryModuleBuilder()
      .implement(classOf[FieldInterface], classOf[gridBaseImpl.Field])
      .build(classOf[FieldFactory])
    )

    install(new FactoryModuleBuilder()
      .implement(classOf[GridInterface], classOf[gridBaseImpl.Grid])
      .build(classOf[GridFactory])
    )

    install(new FactoryModuleBuilder()
      .implement(classOf[NeighborInterface], classOf[gridBaseImpl.Neighbors])
      .build(classOf[NeighborFactory])
    )

    bind[TerritoryInterface].to[gridBaseImpl.Territory]

    bind[FileIOInterface].to[fileIoJSONimpl.FileIO]
  }
}
