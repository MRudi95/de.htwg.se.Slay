package de.htwg.se.slay

import com.google.inject.AbstractModule
import com.google.inject.assistedinject.FactoryModuleBuilder
import de.htwg.se.slay.model.fileIOComponent._
import de.htwg.se.slay.model.gridComponent._
import net.codingwell.scalaguice._

class PlayerModule extends AbstractModule with ScalaModule{
  override def configure(): Unit = {
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
