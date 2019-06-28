package de.htwg.se.slay.controller

import de.htwg.se.slay.model._
import de.htwg.se.slay.util.Command

class MoveCommand(f1: Field, f2: Field, ctrl:Controller) extends Command{
  var gp1Mem: GamePiece = f1.gamepiece

  var gp2Mem: GamePiece = f2.gamepiece
  var ownerMem: Player  = f2.owner
  var terMem: Territory  = f2.territory

  var cmbTerList: List[Territory] = List(f1.territory)
  var splitTerList: List[Set[Field]] = Nil

  var caplist: List[Option[Field]] = Nil
  var biggestTer: Territory = _



  override def doStep(): Unit = {
    gp1Mem = f1.gamepiece
    gp2Mem = f2.gamepiece
    ownerMem = f2.owner
    terMem = f2.territory

    f1.gamepiece = NoPiece()
    f2.gamepiece = gp1Mem
    f2.gamepiece.asInstanceOf[UnitGamePiece].hasMoved = true
    f2.owner = f1.owner

    f2.territory.removeField(f2)
    f2.territory = f1.territory
    f2.territory.addField(f2)


    f2.neighbors.foreach(x => (x.owner, x.territory) match{
      case (o, ter) if o == f2.owner && ter == f2.territory =>
      case (o, ter) if o == f2.owner && !cmbTerList.contains(ter) => cmbTerList = ter::cmbTerList
      case (o, _) if o == ownerMem => splitTerList = Set(x)::splitTerList
      case _ =>
    })
    biggestTer = cmbTerList.maxBy(_.size())
    cmbTerList = cmbTerList.filterNot(_ == biggestTer)


    caplist = cmbTerList.map(_.fields.find(_.gamepiece.isInstanceOf[Capital]))
    caplist.foreach {
      case Some(field) =>
        field.gamepiece = NoPiece()
        ctrl.capitals -= field
      case None =>
    }

    cmbTerList.foreach{ ter =>
      if(ter.capital != null) biggestTer.capital.balance += ter.capital.balance
      ter.fields.foreach{ f =>
        f.territory = biggestTer
        biggestTer.addField(f)}
    }


    splitTerList = splitTerList.map(list => recursion(list.head, list))
    splitTerList.foreach(_ =>
      splitTerList match{
        case head::rest if rest.exists(_.contains(head.head)) => splitTerList = rest
        case head::rest if !rest.exists(_.contains(head.head)) => splitTerList = rest :+ head
      }
    )

    var splitTerritory: List[Territory] = List(terMem)
    terMem.fields.find(_.gamepiece.isInstanceOf[Capital]) match{
      case Some(field) =>
        splitTerList.foreach(list =>
          if(!list.contains(field)) {
            list.foreach(terMem.removeField(_))
            val tmp_ter = new Territory()
            list.foreach { f =>
              tmp_ter.addField(f)
              f.territory = tmp_ter
              f.gamepiece match {
                case gp:UnitGamePiece => tmp_ter.addUnit(gp)
                case _ =>
              }
            }
          }

        )
      case None =>
    }

  }

  def recursion(f: Field, list: Set[Field]): Set[Field] ={
    var reclist = list + f
    f.neighbors.foreach(field =>
      if(field != null && field.owner == ownerMem && !reclist.contains(field))
        reclist ++= recursion(field, reclist)
    )

    reclist
  }



  override def undoStep(): Unit = {
    val tmp_gp1 = f1.gamepiece
    val tmp_gp2 = f2.gamepiece
    val tmp_owner = f2.owner
    val tmp_ter = f2.territory

    f1.gamepiece = gp1Mem
    f1.gamepiece.asInstanceOf[UnitGamePiece].hasMoved = false
    f2.gamepiece = gp2Mem
    f2.owner = ownerMem

    f2.territory.removeField(f2)
    f2.territory = terMem
    f2.territory.addField(f2)

    gp1Mem = tmp_gp1
    gp2Mem = tmp_gp2
    ownerMem = tmp_owner
    terMem = tmp_ter


    cmbTerList.foreach{ ter =>
      if(ter.capital != null) biggestTer.capital.balance -= ter.capital.balance
      ter.fields.foreach{ f =>
        f.territory = ter
        biggestTer.removeField(f)}
    }

    caplist.foreach {
      case Some(field) =>
        cmbTerList.find(_ == field.territory) match {
          case Some(ter) =>
            field.gamepiece = ter.capital
            ctrl.capitals += field
          case None =>
        }
      case None =>
    }

  }

  override def redoStep(): Unit = {
    val tmp_gp1 = f1.gamepiece
    val tmp_gp2 = f2.gamepiece
    val tmp_owner = f2.owner
    val tmp_ter = f2.territory

    f1.gamepiece = gp1Mem
    f2.gamepiece = gp2Mem
    f2.gamepiece.asInstanceOf[UnitGamePiece].hasMoved = true
    f2.owner = ownerMem

    f2.territory.removeField(f2)
    f2.territory = terMem
    f2.territory.addField(f2)

    gp1Mem = tmp_gp1
    gp2Mem = tmp_gp2
    ownerMem = tmp_owner
    terMem = tmp_ter


    caplist.foreach {
      case Some(field) =>
        field.gamepiece = NoPiece()
        ctrl.capitals -= field
      case None =>
    }

    cmbTerList.foreach{ ter =>
      if(ter.capital != null) biggestTer.capital.balance += ter.capital.balance
      ter.fields.foreach{ f =>
        f.territory = biggestTer
        biggestTer.addField(f)}
    }

  }
}