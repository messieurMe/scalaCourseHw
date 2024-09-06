package weekday

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class WeekDaySpec extends AnyFlatSpec with Matchers {
  "WeekDay isWeekend" should "correctly work for weekend" in {
    WeekDay.isWeekend(WeekDay.fromInt(6)) shouldBe true
    WeekDay.isWeekend(WeekDay.fromInt(5)) shouldBe true
  }

  "WeekDay isWeekday" should "correctly work for weekend" in {
    WeekDay.isWeekday(WeekDay.fromInt(6)) shouldBe false
  }

  it should "correctly work for weekday" in {
    WeekDay.isWeekday(WeekDay.fromInt(0)) shouldBe true
  }

  "WeekDay daysToWeekend" should "correctly work for weekend" in {
    WeekDay.daysToWeekend(WeekDay.fromInt(6)) shouldBe 0
    WeekDay.daysToWeekend(WeekDay.fromInt(5)) shouldBe 0
  }

  it should "correctly work for weekday" in {
    WeekDay.daysToWeekend(WeekDay.fromInt(4)) shouldBe 1
    WeekDay.daysToWeekend(WeekDay.fromInt(0)) shouldBe 5
  }
}
