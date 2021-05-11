package de.htwg.se.slay

import com.google.inject.AbstractModule
import de.htwg.se.slay.model.persistenceComponent._
import net.codingwell.scalaguice._

class PlayerModule extends AbstractModule with ScalaModule{
  override def configure(): Unit = {
    bind[PlayerPersistenceInterface].to[mongoImplementation.PlayerPersistence]
  }
}
