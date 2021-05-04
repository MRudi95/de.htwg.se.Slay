package de.htwg.se.slay

import com.google.inject.{Guice, Injector}
import de.htwg.se.slay.aview.{RestAPI, SwingGUI, TextUI}
import de.htwg.se.slay.controller.controllerComponent.ControllerInterface

import scala.io.StdIn.readLine

object  Slay{
  val injector: Injector = Guice.createInjector(new SlayModule)
  val controller: ControllerInterface = injector.getInstance(classOf[ControllerInterface])
//  val gui = new SwingGUI(controller)
  val tui = new TextUI(controller)
  val restapi = new RestAPI(controller)

  controller.createGrid("Map1")

  def main(args: Array[String]) : Unit = {
    //RestAPI
    new Thread(() => {
      restapi.run()
    }).start()

    //normal gameinputs/gameplay
    var input: String = ""
    controller.nextturn()
    do{
      input = readLine()
      tui.processInput(input)
    }while(input != "q" && input != "quit")
    System.exit(0)
  }
}