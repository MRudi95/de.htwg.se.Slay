package de.htwg.se.slay.controller.controllerComponent.controllerBaseImpl

import com.google.inject.Guice
import de.htwg.se.slay.SlayModule
import de.htwg.se.slay.model.gamepieceComponent.{Capital, GamePiece, NoPiece, UnitGamePiece}
import de.htwg.se.slay.model.gridComponent.{FieldInterface, TerritoryInterface}
import de.htwg.se.slay.model.playerComponent.Player
import de.htwg.se.slay.util.Command

import scala.util.Random

class MoveCommand(f1: FieldInterface, f2: FieldInterface, ctrl:Controller) extends Command {
  var gp1Mem: GamePiece = f1.gamepiece

  var gp2Mem: GamePiece = f2.gamepiece
  var ownerMem: Player = f2.owner
  var terMem: Option[TerritoryInterface] = f2.territory

  var cmbTerList: List[Option[TerritoryInterface]] = List(f1.territory)
  var splitTerList: List[Set[FieldInterface]] = Nil

  var caplist: List[Option[FieldInterface]] = Nil
  var biggestTer: Option[TerritoryInterface] = None

  var splitTer: Option[TerritoryInterface] = terMem
  var splitTerritory: List[(TerritoryInterface, FieldInterface, GamePiece)] = List((splitTer match {
    case Some(value) => value
  }, null, null))

  def makeMove() = {
    caplist.foreach {
    case Some(field) =>
      field.gamepiece = NoPiece()
      ctrl.capitals -= field
    case None =>
    }
  }

  def makeTeritory() = {
    cmbTerList.foreach {
      case Some(ter) =>
        if (ter.capital != null) {
          biggestTer match {
            case Some(biggestTer) =>
              biggestTer.capital.balance += ter.capital.balance
              biggestTer.armyCost += ter.armyCost
          }
        }
        ter.fields.foreach { f =>
          f.territory = biggestTer
          biggestTer match {
            case Some(value) => value.addField(f)
          }
        }
      case None =>
    }
  }

  def f2Step() = {
    f2.owner = ownerMem
//    f2.territory.removeField(f2)
    for(x <- f2.territory) yield x.removeField(f2)
    f2.territory = terMem
//    f2.territory.addField(f2)
    for(x <- f2.territory) yield x.addField(f2)
  }

  def stepSetup() = {
    (f1.gamepiece, f2.gamepiece, f2.owner, f2.territory)
  }

  def gamepieceSetup(hasMoved: Boolean) = {
    f1.gamepiece = gp1Mem
    f2.gamepiece = gp2Mem
    f2.gamepiece = f2.gamepiece match{
      case gp:UnitGamePiece => gp.copyTo(hasMoved)
      case gp:GamePiece => gp
    }
  }

  override def doStep(): Unit = {
    gp1Mem = f1.gamepiece
    gp2Mem = f2.gamepiece
    ownerMem = f2.owner
    terMem = f2.territory
    gp2Mem match {
      case _: UnitGamePiece => for(x <- terMem) yield x.removeUnit _ //terMem.removeUnit(_)
      case _: Capital => ctrl.capitals -= f2
      case _ =>
    }

    f1.gamepiece = NoPiece()
    f2.gamepiece = gp1Mem
    f2.gamepiece = f2.gamepiece match{
      case gp:UnitGamePiece => gp.copyTo(true)
      case gp:GamePiece => gp
    }
    f2.owner = f1.owner

//    f2.territory.removeField(f2)
    for(x <- f2.territory) yield x.removeField(f2)
    f2.territory = f1.territory
//    f2.territory.addField(f2)
    for(x <- f2.territory) yield x.addField(f2)


    f2.neighbors.foreach {
      case Some(x) =>
        (x.owner, x.territory) match {
          case (o, ter) if o == f2.owner && ter == f2.territory =>
          case (o, ter) if o == f2.owner && !cmbTerList.contains(ter) => cmbTerList = ter :: cmbTerList
          case (o, _) if o == ownerMem => splitTerList = Set(x) :: splitTerList
          case _ =>
        }
      case None =>
    }
    biggestTer = cmbTerList.maxBy {
      case Some(value) => value.size
    }
    cmbTerList = cmbTerList.filterNot(_ == biggestTer)


//    caplist = cmbTerList.map(_.fields.find(_.gamepiece.isInstanceOf[Capital]))
    caplist = cmbTerList.flatMap(_.map(_.fields.find(_.gamepiece.isInstanceOf[Capital])))
    makeMove()

    makeTeritory()


    splitTerList = splitTerList.map(list => recursion(list.head, list))
    splitTerList.foreach(_ =>
      splitTerList match {
        case head :: rest if rest.exists(_.contains(head.head)) => splitTerList = rest
        case head :: rest if !rest.exists(_.contains(head.head)) => splitTerList = rest :+ head
      }
    )

    terMem match {
      case Some(terMem) =>
        terMem.fields.find(_.gamepiece.isInstanceOf[Capital]) match {
          case Some(field) =>
            splitTerList.foreach { list =>
              if (!list.contains(field)) {
                splitTer match {
                  case Some(splitTer) =>
                    list.foreach(splitTer.removeField)
                    splitTer.fields.foreach(_.territory = Some(splitTer))
                    splittingTerritories(list)
                }
              }
            }
          case None =>
            splitTerritory = List()
            splitTerList.foreach(list => splittingTerritories(list))
        }
    }

  }

  private def recursion(f: FieldInterface, list: Set[FieldInterface]): Set[FieldInterface] = {
    var recList = list + f
    f.neighbors.foreach {
      case Some(field) if field.owner == ownerMem && !recList.contains(field) => recList ++= recursion(field, recList)
      case _ =>
    }
    recList
  }

  private def splittingTerritories(list: Set[FieldInterface]): Unit = {
    val tmp_ter = Guice.createInjector(new SlayModule).getInstance(classOf[TerritoryInterface])
    list.foreach { f =>
      tmp_ter.addField(f)
      f.territory = Some(tmp_ter)
      f.gamepiece match {
        case gp: UnitGamePiece =>
          for(x <- splitTer) yield x.removeUnit(gp)
          tmp_ter.addUnit(gp)
        case _ =>
      }
    }
    var tmp_gp: GamePiece = null
    var newCap: FieldInterface = null
    if (tmp_ter.size >= 2) {
      tmp_ter.fields.find(_.gamepiece.isInstanceOf[NoPiece]) match {
        case Some(field) =>
          newCap = field
        case None =>
          newCap = tmp_ter.fields.toIndexedSeq(Random.nextInt(tmp_ter.fields.size))
      }
      tmp_gp = newCap.gamepiece
      newCap.gamepiece = new Capital(newCap.owner)
      tmp_ter.setCapital(newCap)
      tmp_ter.capital.balance = 0
      ctrl.capitals += newCap
    }
    splitTerritory = splitTerritory :+ (tmp_ter, newCap, tmp_gp)
  }


  override def undoStep(): Unit = {
    val (tmp_gp1, tmp_gp2, tmp_owner, tmp_ter) = stepSetup()

    gamepieceSetup(false)
    f2Step()

    gp2Mem match {
      case _: UnitGamePiece => for(x <- f2.territory) yield x.addUnit _ //f2.territory.addUnit(_)
      case _: Capital => ctrl.capitals += f2
      case _ =>
    }

    gp1Mem = tmp_gp1
    gp2Mem = tmp_gp2
    ownerMem = tmp_owner
    terMem = tmp_ter


    cmbTerList.foreach {
      case Some(ter) =>
        if (ter.capital != null) {
          biggestTer match {
            case Some(biggestTer) =>
              biggestTer.capital.balance -= ter.capital.balance
              biggestTer.armyCost -= ter.armyCost
          }

        }
        ter.fields.foreach { f =>
          f.territory = Some(ter)
          biggestTer match {
            case Some(value) => value.removeField(f)
          }
        }
    }

    caplist.foreach {
      case Some(field) =>
        cmbTerList.find(_ == field.territory) match {
          case Some(Some(ter)) =>
            field.gamepiece = ter.capital
            ctrl.capitals += field
          case None =>
        }
      case None =>
    }


//    terMem.fields.foreach(_.territory = terMem)
    terMem match {
      case Some(value) => value.fields.foreach(_.territory = terMem)
    }
    splitTerritory.foreach {
      case (_, cap, gp) if gp != null =>
        ctrl.capitals -= cap
        cap.gamepiece = gp
      case (_, _, _) =>
    }
  }

  override def redoStep(): Unit = {
    val (tmp_gp1, tmp_gp2, tmp_owner, tmp_ter) = stepSetup()

    gamepieceSetup(true)
    f2Step()

    gp2Mem match {
      case _: UnitGamePiece => for(x <- f2.territory) yield x.removeUnit _ //f2.territory.removeUnit(_)
      case _: Capital => ctrl.capitals -= f2
      case _ =>
    }

    gp1Mem = tmp_gp1
    gp2Mem = tmp_gp2
    ownerMem = tmp_owner
    terMem = tmp_ter

    makeMove()

    makeTeritory()


    splitTerritory.foreach {
      case (ter, cap, _) if cap != null =>
        ctrl.capitals += cap
        cap.gamepiece = ter.capital
      case (ter, _, _) =>
        ter.fields.foreach(_.territory = Some(ter))
    }
  }
}