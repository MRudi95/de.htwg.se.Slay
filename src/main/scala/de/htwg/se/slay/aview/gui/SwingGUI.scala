package de.htwg.se.slay.aview.gui

import java.awt.Color

import de.htwg.se.slay.controller._
import de.htwg.se.slay.model._
import de.htwg.se.slay.util.Observer

import scala.collection.mutable.ListBuffer
import scala.swing._
import scala.swing.Swing._
import scala.swing.event.{ButtonClicked, MouseClicked}


class SwingGUI(controller: Controller) extends Frame with Observer{
  controller.add(this)

  val icons = "src/main/resources/icons/"
  val water: Color = new Color(84, 214, 247)  //blue
  val p1Color: Color = new Color(242, 242, 58) //yellow
  val p2Color: Color = new Color(52, 226, 58) //green

  var fields: Vector[Label] = Vector.empty

  title = "Slay Scala"
  visible = true
  override def closeOperation(): Unit = System.exit(0)

  var hiliteList: ListBuffer[Label] = ListBuffer.empty


  val welcomePanel: GridPanel = new GridPanel(3, 1){
    background = new Color(50, 50, 50) //dark gray
    preferredSize = (480, 240)

    val yesButton: Button = new Button("YES"){
      background = new Color(48, 219, 48) //green
    }
    val noButton: Button = new Button("no"){
      background = new Color(232, 74, 74) //red
    }
    listenTo(yesButton, noButton)

    reactions += {
      case ButtonClicked(this.yesButton) =>
        StateStartUp.handle(ReadPlayerName(1), controller)
      case ButtonClicked(this.noButton) =>
        System.exit(0)
    }

    contents += new Label("WELCOME TO SLAY"){
      foreground = new Color(222, 222, 222) //white
      font = new Font("Arial", 1, 36)
    }
    contents += new Label("Do You want to play a Game?"){
      foreground = new Color(222, 222, 222) //white
      font = new Font("Arial", 0, 20)
    }
    contents += new FlowPanel(){
      opaque = false
      contents += (yesButton, noButton)
    }
  }

  def readPlayerPanel(player:Int): GridPanel = new GridPanel(3, 1){
    background = new Color(50, 50, 50)
    preferredSize = (480, 240)

    val nameField: TextField = new TextField(30)
    val okButton: Button = new Button("OK")
    listenTo(okButton)
    reactions += {
      case ButtonClicked(this.okButton) if nameField.text == "" =>
        controller.addPlayer(new Player("Player " + player , "\033[10" + (4-player) + "m"))
        StateStartUp.handle(ReadPlayerName(player+1), controller)
      case ButtonClicked(this.okButton) =>
        controller.addPlayer(new Player(nameField.text, "\033[10" + (4-player) + "m"))
        StateStartUp.handle(ReadPlayerName(player+1), controller)
    }

    contents += new Label("Player " + player + " enter your name:"){
      foreground = new Color(222, 222, 222)
      font = new Font("Arial", 0, 20)
    }
    contents += new BorderPanel(){
      opaque = false
      add(new FlowPanel(nameField){
        opaque = false
      }, BorderPanel.Position.South)
    }
    contents += new FlowPanel(okButton){
      opaque = false
    }


  }

  val menu: MenuBar = new MenuBar{
    contents += new Menu("Edit"){
      contents += new MenuItem(Action("Undo"){ controller.undo() })
      contents += new MenuItem(Action("Redo"){ controller.redo() })
    }
    contents += new Menu("Names"){
      contents += new MenuItem(Action("Player 1"){
        val res = Dialog.showInput(this, "Enter your new name:", "Name Change", Dialog.Message.Info, initial = "")
        res match {
          case Some(name) =>
            controller.changeName(name, 1)
          case None =>
        }
      })
      contents += new MenuItem(Action("Player 2"){
        val res = Dialog.showInput(this, "Enter your new name:", "Name Change", Dialog.Message.Info, initial = "")
        res match {
          case Some(name) =>
            controller.changeName(name, 2)
          case None =>
        }
      })
    }
  }

  var gridPanel: GridPanel = new GridPanel(1, 1)

  val buttonPanel: FlowPanel = new FlowPanel(){
    opaque = false

    val buyButton: Button = new Button("Buy"){
//      enabled = false
//      listenTo(mouse.clicks)
//      reactions += {
//        case _: MouseClicked =>
//          if (hiliteList.length == 1)
//            enabled = true
//          else
//            enabled = false
//      }
    }
    val combineButton: Button = new Button("Combine")
    val moveButton: Button = new Button("Move")
    val castleButton: Button = new Button("Castle")
    val balButton: Button = new Button("Balance")
    val endButton: Button = new Button("End Turn")
    val surButton: Button = new Button("Surrender")

    contents += (buyButton, combineButton, moveButton, castleButton, balButton, endButton, surButton)
    listenTo(buyButton, combineButton, moveButton, castleButton, balButton, endButton, surButton)

    reactions += {
      case ButtonClicked(this.buyButton) if hiliteList.length == 1 =>
        controller.buyPeasant(hiliteList.head.name.toInt)
      case ButtonClicked(this.combineButton) if hiliteList.length == 2 =>
        controller.combineUnit(hiliteList.head.name.toInt, hiliteList(1).name.toInt)
      case ButtonClicked(this.moveButton) if hiliteList.length == 2 =>
        controller.moveUnit(hiliteList.head.name.toInt, hiliteList(1).name.toInt)
      case ButtonClicked(this.castleButton) if hiliteList.length == 1 =>
        controller.placeCastle(hiliteList.head.name.toInt)
      case ButtonClicked(this.balButton) if hiliteList.length == 1 =>
        controller.seeBalance(hiliteList.head.name.toInt)
      case ButtonClicked(this.endButton) =>
        controller.nextturn()
      case ButtonClicked(this.surButton) =>
        surrender()
      case _ =>
    }
  }

  val turnField: TextField = new TextField(){
    editable = false
    opaque = false
    foreground = new Color(222, 222, 222) //white
    font = new Font("Arial", 0, 20)
  } //maybe use later

  val statusField: TextField = new TextField(){
    editable = false
    opaque = false
    foreground = new Color(222, 222, 222) //white
    font = new Font("Arial", 0, 20)
  }


  def gameScreen(): Unit ={
    gridPanel = createGridPanel
    contents = new BorderPanel {
      add(menu, BorderPanel.Position.North)
      add(gridPanel, BorderPanel.Position.Center)
      add(new GridPanel(2, 1){
        background = new Color(50, 50, 50) //dark gray
        contents += (buttonPanel, statusField)
      }, BorderPanel.Position.South)
    }
  }

  def createGridPanel: GridPanel = new GridPanel(controller.grid.rowIdx+1, controller.grid.colIdx+1){
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
          case _          => null
        }

        name = idx.toString
        font = new Font("Arial", 1, 22)
        foreground = Color.RED

        opaque = true
        preferredSize = new Dimension(64, 64)

        listenTo(mouse.clicks)
        reactions += {
          case mc:MouseClicked =>
            mc.peer.getButton match {
              case java.awt.event.MouseEvent.BUTTON1 if hiliteList.isEmpty =>
                border = LineBorder(Color.WHITE, 3)
                hiliteList += this
              case java.awt.event.MouseEvent.BUTTON1 if hiliteList.length == 1 =>
                border = LineBorder(Color.RED, 3)
                if(!hiliteList.contains(this)) hiliteList += this
              case java.awt.event.MouseEvent.BUTTON3 =>
                border = LineBorder(Color.BLACK, 1)
                hiliteList -= this
              case _ =>
            }
        }
      }
      fields = fields :+ field
      contents += field

      idx += 1
    }
  }

  def redraw(): Unit ={
    for{f <- fields}{
      val idx = f.name.toInt
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
        case _          => null
      }

      controller.grid(idx).gamepiece match {
        case unit:UnitGamePiece if unit.hasMoved => f.text = "."
        case unit:UnitGamePiece if !unit.hasMoved => f.text = ""
        case _ => f.text = ""
      }
    }
  }

  def surrender(): Unit = {
    val res = Dialog.showConfirmation(this,
      "Do you really want to surrender?",
      optionType=Dialog.Options.YesNo)
    if (res == Dialog.Result.Ok)
      controller.surrender()
  }


  override def update(e: Event): Boolean = {
    e match{
      case _: SuccessEvent =>
        redraw(); true
      case _: GridCreatedEvent =>
        gameScreen(); true
      case _: WelcomeEvent =>
        contents = welcomePanel; true
      case r: ReadPlayerEvent =>
        contents = readPlayerPanel(r.player); true
      case _: MoneyErrorEvent =>
        statusField.text = "Not enough Money!"; true
      case p: PlayerEvent =>
        statusField.text = "It is your turn " + p.name.toUpperCase + " !"; true
      case b: BalanceEvent =>
        statusField.text = "Balance: " + b.bal + "\tIncome: " + b.inc + "\tArmyCost: " + b.cost; true
      case _: OwnerErrorEvent =>
        statusField.text = "You are not the Owner of this!"; true
      case _: GamePieceErrorEvent =>
        statusField.text = "There already is a GamePiece there!"; true
      case _: CombineErrorEvent =>
        statusField.text = "Can't combine those Units!"; true
      case m: MoveErrorEvent =>
        statusField.text = "Can't move there! " + m.reason; true
      case _: MovableErrorEvent =>
        statusField.text = "This Unit is not movable!"; true
      case _: MovedErrorEvent =>
        statusField.text = "This Unit has already moved this turn!"; true
      case _: UndoErrorEvent =>
        statusField.text = "Nothing to undo!"; true
      case _: RedoErrorEvent =>
        statusField.text = "Nothing to redo!"; true
      case v: VictoryEvent =>
        Dialog.showMessage(this, v.name.toUpperCase + " has won the Game!", title="VICTORY"); true
      case _ => false
    }
  }
}
