import Dependencies.*

ThisBuild / scalaVersion := "3.4.0"
ThisBuild / version      := "0.1.0-SNAPSHOT"

Compile / compile / scalacOptions ++= Seq(
  "-Werror",
  "-Wunused:all",
  "-Wvalue-discard",
  "-unchecked",
  "-deprecation",
  "-Ysafe-init",
  "-Ykind-projector"
)

lazy val tictactoe = (project in file("tictactoe"))
  .settings(
    name := "tictactoe",
    libraryDependencies ++= List(
      scalaTest % Test
    ),
    coverageEnabled                 := true,
    coverageFailOnMinimum           := true,
    coverageMinimumStmtTotal        := 70,
    coverageMinimumBranchTotal      := 70,
    coverageMinimumStmtPerPackage   := 70,
    coverageMinimumBranchPerPackage := 65,
    coverageMinimumStmtPerFile      := 65,
    coverageMinimumBranchPerFile    := 60
  )

lazy val console = (project in file("console"))
  .settings(
    name := "console",
    libraryDependencies ++= List(
      scalaTest % Test
    )
  )
  .dependsOn(tictactoe)

lazy val root = (project in file("."))
  .settings(name := "hw4")
  .aggregate(tictactoe, console)
