package de.htwg.se.slay.aview.gui

import java.awt.Color

import de.htwg.se.slay.controller._
import de.htwg.se.slay.model._
import de.htwg.se.slay.util.Observer

import scala.swing._
import scala.swing.Swing._
import scala.swing.event.MouseClicked

class SwingGUI(controller: Controller) extends Frame with Observer{
  controller.add(this)

  val icons = "src/main/resources/icons/"
  val water: Color = new Color(84, 214, 247)  //blue
  val p1Color: Color = new Color(242, 242, 58) //yellow
  val p2Color: Color = new Color(52, 226, 58) //green

  var fields: Vector[Label] = Vector.empty
  title = "Slay Scala"

  val gridPanel: GridPanel = new GridPanel(controller.grid.rowIdx+1, controller.grid.colIdx+1){
    var idx = 0
    for{f <- controller.grid} {
      val field = new Label(){
        border = LineBorder(Color.BLACK, 1)
        background = {
          if(f.owner == controller.players(0)) water else
          if(f.owner == controller.players(1)) p1Color else
          if(f.owner == controller.players(2)) p2Color else
            Color.WHITE
        }
        icon = f.gamepiece match{
          case _:NoPiece  => null
          case _:Tree     => Icon(icons + "tree.png")
          case _:Capital  => Icon(icons + "capital.png")
        }

        name = idx.toString

        opaque = true
        preferredSize = new Dimension(64, 64)

        listenTo(mouse.clicks)
        reactions += {
          case mc:MouseClicked =>
            mc.peer.getButton match {
              case java.awt.event.MouseEvent.BUTTON1 => border = LineBorder(Color.WHITE, 3)
              case java.awt.event.MouseEvent.BUTTON3 => border = LineBorder(Color.BLACK, 1)
              case _ =>
          }
        }
      }
      fields = fields :+ field
      contents += field

      idx += 1
    }
  }

  contents = new BorderPanel {
    add(gridPanel, BorderPanel.Position.Center)
  }


  visible = true

  def redraw(): Unit ={
    for{f <- fields}{
      val idx = f.name.toInt
      //println(idx)
      f.background = {
        if(controller.grid(idx).owner == controller.players(0)) water else
        if(controller.grid(idx).owner == controller.players(1)) p1Color else
        if(controller.grid(idx).owner == controller.players(2)) p2Color else
          Color.WHITE
      }

      f.icon = controller.grid(idx).gamepiece match{
        case _:NoPiece  => null
        case _:Tree     => Icon(icons + "tree.png")
        case _:Grave    => Icon(icons + "grave.png")
        case _:Capital  => Icon(icons + "capital.png")
        case _:Castle   => Icon(icons + "castle.png")
        case _:Peasant  => Icon(icons + "peasant.gif")
        case _:Spearman => Icon(icons + "spearman.gif")
        case _:Knight   => Icon(icons + "knight.gif")
        case _:Baron    => Icon(icons + "baron.gif")
      }
    }
  }

  override def update(e: Event): Boolean = {
    e match{
      case _: SuccessEvent =>
        redraw(); true
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
