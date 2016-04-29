import com.typesafe.sbt.SbtStartScript

seq(SbtStartScript.startScriptForClassesSettings: _*)

version := "0.1"

scalaVersion := "2.11.8"

resolvers ++= Seq(
  "spray repo" at "http://repo.spray.io/"
)

libraryDependencies ++= {
  Seq(
    "io.spray" %% "spray-can" % "1.3.3",
    "io.spray" %% "spray-routing" % "1.3.3",
    "io.spray" %% "spray-json" % "1.3.2",
    "com.typesafe.akka" %% "akka-actor" % "2.3.11",
    "org.scalatest" % "scalatest_2.11" % "2.2.1"
  )
}


seq(Revolver.settings: _*)
scalacOptions := Seq("-deprecation", "-encoding", "utf8")



