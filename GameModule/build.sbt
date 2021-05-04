name := "GameModule"
organization := "de.htwg.se"
version := "0.1"
scalaVersion := "2.13.1"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.9"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.9" % "test"

libraryDependencies += "org.scala-lang.modules" %% "scala-swing" % "2.1.1"


//libraryDependencies += "com.google.inject" % "guice" % "4.2.11"
libraryDependencies += "net.codingwell" %% "scala-guice" % "4.2.11"
libraryDependencies += "com.google.inject.extensions" % "guice-assistedinject" % "4.2.3"


libraryDependencies += "org.scala-lang.modules" % "scala-xml_2.13" % "1.2.0"
libraryDependencies += "com.typesafe.play" %% "play-json" % "2.9.2"