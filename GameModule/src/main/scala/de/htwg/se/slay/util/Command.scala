package de.htwg.se.slay.util

trait Command {
  def doStep():Unit
  def undoStep():Unit
  def redoStep():Unit
}
