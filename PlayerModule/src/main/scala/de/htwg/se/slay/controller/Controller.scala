package de.htwg.se.slay.controller

import com.google.inject.{Guice, Injector}
import de.htwg.se.slay.PlayerModule

class Controller {
  val injector: Injector = Guice.createInjector(new PlayerModule)

}
