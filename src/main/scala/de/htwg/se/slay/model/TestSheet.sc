def zeroFour(x: Int) =
  if(0 <= x && x <= 4)
    x
  else
    -1

var z = zeroFour(3)

z = zeroFour(5)

z = zeroFour(-13)


var moved: Boolean = false

moved = true

def hasMoved(y: Boolean) = moved = y

hasMoved(false)
moved