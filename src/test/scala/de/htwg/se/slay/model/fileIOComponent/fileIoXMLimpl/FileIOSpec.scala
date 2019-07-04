package de.htwg.se.slay.model.fileIOComponent.fileIoXMLimpl

import de.htwg.se.slay.controller.controllerComponent.controllerMockImpl.Controller
import org.scalatest._

class FileIOSpec extends WordSpec with Matchers {
  "JSON FileIO" should{
    "do stuff" in{
      val xmlIO = new FileIO
      val ctrl = new Controller
      val (p1, p2, s, g) = xmlIO.load("test", ctrl.players)
      xmlIO.save("test", ctrl.players, s, g)
    }
  }
}