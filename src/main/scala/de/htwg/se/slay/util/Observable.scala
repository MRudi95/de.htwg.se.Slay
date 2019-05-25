package de.htwg.se.slay.util

import de.htwg.se.slay.controller.{Event, Success}

class Observable {
  var subscribers: Vector[Observer] = Vector()

  def add(s: Observer): Unit = subscribers = subscribers :+ s

  def remove(s: Observer): Unit = subscribers = subscribers.filterNot(o => o == s)

  def notifyObservers(e: Event = new Success): Unit = subscribers.foreach(o => o.update(e))
}
