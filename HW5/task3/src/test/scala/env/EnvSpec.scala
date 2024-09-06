package env

import cats.Id
import env.Env.EnvMapSyntax
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class EnvSpec extends AnyFlatSpec with Matchers {
  "Env" should "have syntax for Map" in {
    implicit val env: Env[Id] = Map.empty[String, String].toEnv

    getEnvs(Id("HTTP_PORT")) shouldBe Id(None)
  }

  it should "have stub" in {
    implicit val env: Env[Id] = Env.stub

    getEnvs(Id("HTTP_PORT")) shouldBe Id(Some("scala"))
  }

  "Env Map" should "return value from Map" in {
    implicit val env: Env[Id] = Map[String, String](
      "HTTP_PORT"                                                   -> "8080",
      "The Ultimate Question of Life, the Universe, and Everything" -> "42"
    ).toEnv

    getEnvs(Id("HTTP_PORT")) shouldBe Id(Some("8080"))

    getEnvs(Id(Id("https://www.youtube.com/watch?v=ADqLBc1vFwI"))) shouldBe Id(None)
  }
  // Казалось бы, зачем писать одноклеточные тесты, когда можно НЕ писать одноклеточные тесты
  "True env map" should "return None on video with solution of this task" in {
    implicit val env: Env[Id] = Env.env

    getEnvs(Id("https://www.youtube.com/watch?v=2jwvNxBde0o")) shouldBe Id(None)
    getEnvs(Id("PATH")).get shouldBe sys.env("PATH")
  }
  "Dummy **\"test\"**" should "raise coverage" in {
    val mapEnv = new MapEnv[Id](Map[String, String]())
    val envEnv = new EnvEnv[Id]()

    mapEnv.getEnv(Id(1)) shouldBe Id(None)
    envEnv.getEnv(Id(1)) shouldBe Id(None)
  }
}
