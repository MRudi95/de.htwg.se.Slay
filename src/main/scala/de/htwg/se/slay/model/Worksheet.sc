import de.htwg.se.slay.model.{Field, Player, Territory}

val field = new Field(Player("",""))
val field2 = new Field(Player("",""))
val ter1 = new Territory()
val ter2 = new Territory()

ter1.addField(field)
ter1.addField(field2)

ter2.addField(field)

var list = List(ter1, ter2)

val ter3 = list.maxBy(_.size())
list = list.filterNot(_ == ter3)