import Dependencies.*

ThisBuild / scalaVersion := "2.13.12"
ThisBuild / version := "0.1.0-SNAPSHOT"

Compile / compile / scalacOptions ++= Seq(
  "-Werror",
  "-Wdead-code",
  "-Wextra-implicit",
  "-Wnumeric-widen",
  "-Wunused",
  "-Wvalue-discard",
  "-Xlint",
  "-Xlint:-byname-implicit",
  "-Xlint:-implicit-recursion",
  "-unchecked"
)

resolvers += "tom-and-john" at "https://gitlab.com/api/v4/projects/33751126/packages/maven"

lazy val root = (project in file("."))
  .settings(
    name := "hw1",
    dependencyOverrides += "ru.tinkoff" %% "scala-course-clerk" % "0.1",
    libraryDependencies ++= List(
      scalaTest % Test,
      scalaCheck % Test,
      tomScalaCourse,
      johnScalaCourse,
    )
  )
