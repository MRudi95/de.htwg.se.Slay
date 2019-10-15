package de.htwg.se.slay

import com.google.inject.{Guice, Injector}
import de.htwg.se.slay.aview.{SwingGUI, TextUI}
import de.htwg.se.slay.controller.controllerComponent.ControllerInterface

import scala.io.StdIn.readLine

object Slay{
  val injector: Injector = Guice.createInjector(new SlayModule)
  val controller: ControllerInterface = injector.getInstance(classOf[ControllerInterface])
  //val gui = new SwingGUI(controller)
  val tui = new TextUI(controller)

  def main(args: Array[String]) : Unit = {
    //StateStartUp.handle(WelcomeScreen(), controller)

    controller.createGrid("Map1")
    var input: String = ""
    controller.nextturn()
    do{
      input = readLine()
      tui.processInput(input)
    }while(input != "q" && input != "quit")
    System.exit(0)
  }
}