package de.htwg.se.slay.controller

trait StartUp{}

case class WelcomeScreen() extends StartUp
case class ReadPlayerName(player:Int) extends StartUp