lazy val root = (project in file(".")).aggregate(gamemodule, playermodule)
lazy val gamemodule = (project in file("GameModule"))
lazy val playermodule = (project in file("PlayerModule"))