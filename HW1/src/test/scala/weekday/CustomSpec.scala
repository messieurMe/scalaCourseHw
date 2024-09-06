package weekday

import org.scalacheck.Prop.forAll
import org.scalacheck.Properties
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import weekday.WeekDayPropSpec.property

class CustomSpec extends AnyFlatSpec with Matchers {
  "Days between" should "correctly work" in {
    WeekDay.daysBetween(Wednesday, Monday) shouldBe 5
    WeekDay.daysBetween(Monday, Sunday) shouldBe 6
  }
  "From name" should "correctly work" in {
    WeekDay.fromNameOpt("Lol") shouldBe Option.empty
    WeekDay.fromNameOpt("Wednesday") shouldBe Option.apply(Wednesday)
  }
}
