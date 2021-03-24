package de.htwg.se.slay.controller.controllerComponent.controllerMockImpl

import de.htwg.se.slay.controller.controllerComponent.ControllerInterface
import de.htwg.se.slay.model.gridComponent.{FieldInterface, GridInterface}
import de.htwg.se.slay.model.playerComponent.Player

import scala.collection.immutable.HashSet

class Controller extends ControllerInterface{
  var grid: GridInterface = _

  var capitals: HashSet[FieldInterface] = _

  var players: Vector[Player] = Vector(
    new Player("Player0", "\u001b[104m"),
    new Player("Player 1", "\u001b[103m"),
    new Player("Player 2", "\u001b[102m"))

  override def gridToString: String = ""

  override def addPlayer(player: Player): Unit = players = players :+ player

  override def changeName(name: String, player: Int): Unit = {}

  override def createGrid(mapname: String, typ: String): Unit = {}

  override def seeBalance(c: Int): Unit = {}

  override def buyPeasant(c: Int): Unit = {}

  override def placeCastle(c: Int): Unit = {}

  override def combineUnit(c1: Int, c2: Int): Unit = {}

  override def moveUnit(c1: Int, c2: Int): Unit = {}

  override def surrender(): Unit = {}

  override def moneymoney(): Unit = {}

  override def nextturn(): Unit = {}

  override def undo(): Unit = {}

  override def redo(): Unit = {}

  override def save(file: String): Unit = {}

  override def load(file: String): Unit = {}

  override var state: Int = 0
}
