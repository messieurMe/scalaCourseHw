package id

import cats.laws.discipline.MonadTests
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.scalacheck.Checkers
import org.typelevel.discipline.scalatest.FunSuiteDiscipline

class MyIdSpec extends AnyFunSuite with FunSuiteDiscipline with Checkers {
  checkAll(
    name = "MyId.MonadLaws",
    ruleSet = MonadTests[MyId].monad[Int, Int, String]
  )
}
