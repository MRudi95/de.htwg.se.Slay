import de.htwg.se.slay.model.gridComponent.Territory
import de.htwg.se.slay.model.gridComponent.gridBaseImpl.Field
import de.htwg.se.slay.model.playerComponent.Player

val field = new Field(new Player("",""))
val field2 = new Field(new Player("",""))
val ter1 = new Territory()
val ter2 = new Territory()

ter1.addField(field)
ter1.addField(field2)

ter2.addField(field)

var list = List(ter1, ter2)

val ter3 = list.maxBy(_.size)
list = list.filterNot(_ == ter3)