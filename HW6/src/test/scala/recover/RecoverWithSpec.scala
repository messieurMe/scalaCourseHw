package recover

import cats.kernel.Eq
import cats.laws.discipline.MonadTests
import org.scalacheck.{Arbitrary, Gen}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.scalacheck.Checkers
import org.typelevel.discipline.scalatest.FunSuiteDiscipline
import recover.RecoverWith.{nil, some}

class RecoverWithSpec extends AnyFunSuite with FunSuiteDiscipline with Checkers {
  checkAll(
    name = "RecoverWith.MonadLaws",
    ruleSet = MonadTests[RecoverWith].monad[Int, Int, String]
  )

  implicit def eqTree[A: Eq]: Eq[RecoverWith[A]] = Eq.fromUniversalEquals

  implicit def arbTree[A: Arbitrary]: Arbitrary[RecoverWith[A]] =
    Arbitrary(
      Gen.oneOf(
        Gen.const(nil),
        Arbitrary.arbitrary[A].map(some)
      )
    )
}
