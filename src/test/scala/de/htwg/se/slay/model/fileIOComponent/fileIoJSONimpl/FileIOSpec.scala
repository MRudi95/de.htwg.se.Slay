package de.htwg.se.slay.model.fileIOComponent.fileIoJSONimpl

import de.htwg.se.slay.controller.controllerComponent.controllerMockImpl.Controller
import org.scalatest._

class FileIOSpec extends WordSpec with Matchers {
  "JSON FileIO" should{
    "do stuff" in{
      val jsonIO = new FileIO
      val ctrl = new Controller
      val (p1, p2, s, g) = jsonIO.load("test", ctrl.players)
      jsonIO.save("test", ctrl.players, s, g)
    }
  }
}
