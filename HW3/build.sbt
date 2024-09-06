import Dependencies.*

ThisBuild / scalaVersion := "3.4.0"
ThisBuild / version := "0.1.0-SNAPSHOT"

Compile / compile / scalacOptions ++= Seq(
  "-Werror",
  "-Wunused:all",
  "-Wvalue-discard",
  "-unchecked",
  "-deprecation",
  "-Ysafe-init",
  "-Ykind-projector"
)

lazy val root = (project in file("."))
  .settings(
    name := "hw3",
    libraryDependencies ++= List(
      scalaTest % Test
    )
  )
