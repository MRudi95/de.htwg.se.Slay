package de.htwg.se.slay.util

import de.htwg.se.slay.controller.controllerComponent.Event

trait Observer {
  def update(e: Event): Boolean
}

