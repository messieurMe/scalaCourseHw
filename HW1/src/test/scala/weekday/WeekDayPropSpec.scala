package weekday

import org.scalacheck.Prop.forAll
import org.scalacheck.Properties

object WeekDayPropSpec extends Properties("WeekDay") {
  property("convert int to weekday and back") = forAll { day: Int =>
    WeekDay.toInt(WeekDay.fromInt(day)) == (day % 7 + 7) % 7
  }

  property("correctly get next day") = forAll { day: Int =>
    WeekDay.toInt(WeekDay.nextDay(WeekDay.fromInt(day))) == (day % 7 + 8) % 7
  }

  property("correctly get prev day") = forAll { day: Int =>
    WeekDay.toInt(WeekDay.prevDay(WeekDay.fromInt(day))) == (day % 7 + 6) % 7
  }
}
