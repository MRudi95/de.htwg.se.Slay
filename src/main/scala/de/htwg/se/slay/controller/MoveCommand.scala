package de.htwg.se.slay.controller

import de.htwg.se.slay.model._
import de.htwg.se.slay.util.Command

class MoveCommand(f1: Field, f2: Field, ctrl:Controller) extends Command{
  var gp1Mem: GamePiece = f1.gamepiece

  var gp2Mem: GamePiece = f2.gamepiece
  var ownerMem: Player  = f2.owner
  var terMem: Territory  = f2.territory

  var territoryList: List[Territory] = List(f1.territory)

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
      case (o, ter) if o == f2.owner && !territoryList.contains(ter) => territoryList = ter::territoryList
      case _ =>
    })
    biggestTer = territoryList.maxBy(_.size())
    territoryList = territoryList.filterNot(_ == biggestTer)


    caplist = territoryList.map(_.fields.find(_.gamepiece.isInstanceOf[Capital]))
    caplist.foreach {
      case Some(field) =>
        field.gamepiece = NoPiece()
        ctrl.capitals -= field
      case None =>
    }

    territoryList.foreach{ ter =>
      if(ter.capital != null) biggestTer.capital.balance += ter.capital.balance
      ter.fields.foreach{ f =>
        f.territory = biggestTer
        biggestTer.addField(f)}
    }
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


    territoryList.foreach{ ter =>
      if(ter.capital != null) biggestTer.capital.balance -= ter.capital.balance
      ter.fields.foreach{ f =>
        f.territory = ter
        biggestTer.removeField(f)}
    }

    caplist.foreach {
      case Some(field) =>
        territoryList.find(_ == field.territory) match {
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

    territoryList.foreach{ ter =>
      if(ter.capital != null) biggestTer.capital.balance += ter.capital.balance
      ter.fields.foreach{ f =>
        f.territory = biggestTer
        biggestTer.addField(f)}
    }

  }
}