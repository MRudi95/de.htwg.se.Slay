package de.htwg.se.slay.controller

trait PlayerTurn{}

case class Player0Turn() extends PlayerTurn
case class Player1Turn() extends PlayerTurn
case class Player2Turn() extends PlayerTurn