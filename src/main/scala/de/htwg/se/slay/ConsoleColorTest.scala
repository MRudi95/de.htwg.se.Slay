package de.htwg.se.slay

import de.htwg.se.slay.model.{Peasant, Player}

object ConsoleColorTest {
  val W = "\033[44m" //Color Water (blue)
  val P1 = "\033[43m" //Color Player1 (yellow)
  val P2 = "\033[42m" // Color Player2 (green)
  val R = "\033[0m" //Color Reset
  val B = "\033[97m" //Text Color Black

  def main(args: Array[String]): Unit = {
    println("\033[32mTest")
    println("\033[1;32mTest")
    println("\033[1;34mTest")

    //FieldTest
    println("\033[42m\033[34m+---+\033[0m")
    println("\033[43m| 1 |\033[0m")
    println("\033[44m\033[97m+---+\033[0m")

    //GamePiece explanantion
    println()
    println("T = Tower")
    println("B = Tree")
    println("C = City/Capital")
    println("G = Grave")
    println("Units:")
    println("1 = Peasant")
    println("2 = Spearman")
    println("3 = Knight")
    println("4 = Baron")
    //TestPlayingField 5 x 6
    println(W + "     " + R + " " + W + "     " + P1 + "  \033[43m\033[97m1\033[43m  " + P2 + "     " + P2 + "     " + W + "     " + R)
    println(W + "\033[4;97m     " + R + " " + W + "\033[4;97m     " + P1 + "\033[4;97m     " + P2 + "\033[4;97m     " + P2 + "\033[4;97m     " + W + "\033[4;97m     " + R)
    println(P1 + "     " + R + " " + W + "     " + P1 + "  \033[43m\033[97m1\033[43m  " + P2 + "     " + P2 + "     " + W + "     " + R)
    println(P1 + "\033[4;97m     " + R + " " + W + "\033[4;97m     " + P1 + "\033[4;97m     " + P2 + "\033[4;97m     " + P2 + "\033[4;97m     " + W + "\033[4;97m     " + R)
    println(W + "     " + R + " " + W + "     " + P1 + "  \033[43m\033[97m1\033[43m  " + P2 + "     " + P2 + "     " + W + "     " + R)
    println(W + "\033[4;97m     " + R + " " + W + "     " + P1 + "     " + P2 + "     " + P2 + "     " + W + "     " + R)
    println(W + "     " + R + " " + W + "     " + P1 + "  \033[43m\033[97m1\033[43m  " + P2 + "     " + P2 + "     " + W + "     " + R)
    println(W + "\033[4;97m     " + R + " " + W + "     " + P1 + "     " + P2 + "     " + P2 + "     " + W + "     " + R)
    println(W + "     " + R + " " + W + "     " + P1 + "  \033[43m\033[97m1\033[43m  " + P2 + "     " + P2 + "     " + W + "     " + R)
    println(W + "\033[4;97m     " + R + " " + W + "     " + P1 + "     " + P2 + "     " + P2 + "     " + W + "     " + R)
    //Single Field:
    // Color + "  " + Color + B + Text + "  " + R
    // Color + Underline + "     " + R
  }
}
