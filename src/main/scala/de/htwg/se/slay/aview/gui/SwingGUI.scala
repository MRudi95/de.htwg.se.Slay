package de.htwg.se.slay.aview.gui

import de.htwg.se.slay.controller._
import de.htwg.se.slay.model.{Capital, NoPiece, Tree}
import de.htwg.se.slay.util.Observer
import javax.swing.ImageIcon

import scala.swing._
import scala.swing.Swing.LineBorder
import scala.swing.event.MouseClicked

class SwingGUI(controller: Controller) extends Frame with Observer{
  val iconLoc = "src/main/resources/icons/"

  title = "Slay Scala"

  def gridPanel: GridPanel = new GridPanel(controller.grid.rowIdx+1, controller.grid.colIdx+1){
    var idx = 0
    for{f <- controller.grid} {
      contents += new Label(){
        border = LineBorder(java.awt.Color.BLACK, 1)
        background = {
          if(f.owner == controller.players(0)) java.awt.Color.BLUE else
          if(f.owner == controller.players(1)) java.awt.Color.YELLOW else
          if(f.owner == controller.players(2)) java.awt.Color.GREEN else
            java.awt.Color.WHITE
        }
        icon = f.gamepiece match{
          case _:NoPiece  => null
          case _:Tree     => new ImageIcon(iconLoc + "tree.png")
          case _:Capital  => new ImageIcon(iconLoc + "capital.png")
        }
        name = idx.toString
        opaque = true
        preferredSize = new Dimension(64, 64)

        listenTo(mouse.clicks)
        reactions += {
          case mc:MouseClicked =>
            mc.peer.getButton match {
              case java.awt.event.MouseEvent.BUTTON1 => border = LineBorder(java.awt.Color.WHITE, 2)
              case java.awt.event.MouseEvent.BUTTON3 => border = LineBorder(java.awt.Color.BLACK, 1)
              case _ =>
          }
        }
      }
      idx += 1
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
