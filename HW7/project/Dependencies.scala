import sbt.*

object Dependencies {

  val scalaTest  = "org.scalatest" %% "scalatest"                     % "3.2.17"
  val catsTest   = "org.typelevel" %% "cats-effect-testing-scalatest" % "1.5.0"
  val catsEffect = "org.typelevel" %% "cats-effect"                   % "3.5.4"

}
