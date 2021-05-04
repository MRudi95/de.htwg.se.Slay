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

libraryDependencies += "com.typesafe.akka" %% "akka-http"   % "10.1.12"
libraryDependencies += "com.typesafe.akka" %% "akka-stream" % "2.6.5"
libraryDependencies += "de.heikoseeberger" %% "akka-http-play-json" % "1.32.0"


libraryDependencies += "org.mongodb.scala" %% "mongo-scala-driver" % "2.9.0"


libraryDependencies ++= Seq(
  "com.typesafe.slick" % "slick_2.13" % "3.3.3",
  "org.slf4j" % "slf4j-api" % "1.7.25",
  "com.typesafe.slick" % "slick-hikaricp_2.13" % "3.3.3"
)

libraryDependencies += "org.postgresql" % "postgresql" % "42.2.13"