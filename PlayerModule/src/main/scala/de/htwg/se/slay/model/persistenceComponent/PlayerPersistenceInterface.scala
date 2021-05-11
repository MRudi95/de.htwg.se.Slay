package de.htwg.se.slay.model.persistenceComponent

import de.htwg.se.slay.model.playerComponent.Player

trait PlayerPersistenceInterface {
  def create(player: Player): Player

  def read(playerId: String): Option[Player]

  def update(player: Player): Unit

  def delete(player: Player): Unit
}