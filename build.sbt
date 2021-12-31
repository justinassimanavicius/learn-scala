name := "playground"

version := "0.1"

scalaVersion := "2.13.6"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.8" % Test
libraryDependencies += "com.lihaoyi" %% "upickle" % "1.3.8"
libraryDependencies += "org.specs2" %% "specs2-core" % "4.12.10" % "test"
libraryDependencies += "io.monix" %% "monix" % "3.4.0"

scalacOptions in Test ++= Seq("-Yrangepos")