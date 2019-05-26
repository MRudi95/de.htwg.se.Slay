package de.htwg.se.slay.controller

import de.htwg.se.slay.util.Command

case class BuyCommand() extends Command{
  override def doStep(): Unit = ???

  override def undoStep(): Unit = ???

  override def redoStep(): Unit = ???
}