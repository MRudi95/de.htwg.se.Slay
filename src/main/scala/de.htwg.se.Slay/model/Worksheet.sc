case class field(a:Int, b:Int)

val field1 = field(1, 1)
field1.a
field1.b

case class Landscape(x:Int , y:Int)

val land = Landscape(30, 30)
land.x
land.y

case class characters(x: Char, y: Int)

val person1 = characters('a', 1)
person1.x
person1.y

val index = 36
(index / 10, index % 10)