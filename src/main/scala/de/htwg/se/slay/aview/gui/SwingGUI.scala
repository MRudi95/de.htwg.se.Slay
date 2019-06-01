package de.htwg.se.slay.aview.gui

import de.htwg.se.slay.controller._
import de.htwg.se.slay.util.Observer


import scala.swing._
import scala.swing.Swing.LineBorder

class SwingGUI(controller: Controller) extends Frame with Observer{

  title = "Slay Scala"

  def gridPanel: GridPanel = new GridPanel(controller.grid.rowIdx+1, controller.grid.colIdx+1){
    for{f <- controller.grid} {
      contents += new FlowPanel(){
        border = LineBorder(java.awt.Color.BLACK, 1)
        background = {
          if(f.owner == controller.players(0)) java.awt.Color.BLUE else
          if(f.owner == controller.players(1)) java.awt.Color.YELLOW else
          if(f.owner == controller.players(2)) java.awt.Color.GREEN else
            java.awt.Color.WHITE
        }
        preferredSize = new Dimension(64, 64)
      }
    }
  }

  contents = new BorderPanel {
    add(gridPanel, BorderPanel.Position.Center)
  }


  visible = true

  override def update(e: Event): Boolean = {
    e match{
      case _: SuccessEvent =>
        println(controller.gridToString); true
      case _: MoneyErrorEvent =>
        println("Not enough Money!"); true
      case p: PlayerEvent =>
        println("It is your turn " + p.name.toUpperCase + " !"); true
      case b: BalanceEvent =>
        println("balance: " + b.bal); true
      case _: OwnerErrorEvent =>
        println("You are not the Owner of this!"); true
      case _: GamePieceErrorEvent =>
        println("There already is a GamePiece there!"); true
      case _: UnitErrorEvent =>
        println("Can't combine those Units!"); true
      case _: UndoErrorEvent =>
        println("Nothing to undo!"); true
      case _: RedoErrorEvent =>
        println("Nothing to redo!"); true
    }
  }
}
