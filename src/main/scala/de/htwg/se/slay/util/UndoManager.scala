package de.htwg.se.slay.util

class UndoManager {
  private var undoStack: List[Command] = Nil
  private var redoStack: List[Command] = Nil

  def doStep():Unit = println()

  def undoStep():Unit = println()

  def redoStep():Unit = println()

  def reset():Unit = {
    undoStack = Nil
    redoStack = Nil
  }
}
