package func

import cats.Eq
import cats.laws.discipline.MonadTests
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.scalacheck.Checkers
import org.typelevel.discipline.scalatest.FunSuiteDiscipline

class FuncSpec extends AnyFunSuite with FunSuiteDiscipline with Checkers {
  checkAll(
    name = "Function1.MonadLaws",
    ruleSet = MonadTests[Function1[Int, *]].monad[Int, Int, String]
  )

  implicit def eqFuncInt[A: Eq]: Eq[Int => A] = (x: Int => A, y: Int => A) =>
    // Check eq for two prime numbers and one composite number
    Eq[A].eqv(x(1423), y(1423)) &&
      Eq[A].eqv(x(2423), y(2423)) &&
      Eq[A].eqv(x(1242), y(1242))
}
