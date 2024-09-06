package date

import date.DateFormat._

object Converter {

  private val d2 = "(\\d{1,2})"
  private val d4 = "(\\d{4})"
  private val rusPattern = s"$d2\\.$d2\\.$d4 $d2:$d2:$d2".r.unanchored
  private val usaPattern = s"$d2-$d2-$d4 $d2:$d2:$d2".r.unanchored
  private val engPattern = s"$d2-$d2-$d4 $d2:$d2:$d2".r.unanchored
  private val itaPattern = s"$d2/$d2/$d4 $d2\\.$d2\\.$d2".r.unanchored
  private val chePattern = s"$d2.$d2.$d4 $d2,$d2,$d2".r.unanchored
  private val swePattern = s"$d4-$d2-$d2 $d2\\.$d2\\.$d2".r.unanchored
  private val svnPattern = s"$d4-$d2-$d2 $d2:$d2:$d2".r.unanchored

  private def toDateTime(
    day: String,
    month: String,
    year: String,
    hour: String,
    minute: String,
    second: String
  ): DateTime = DateTime(
    day.toInt,
    month.toInt,
    year.toInt,
    hour.toInt,
    minute.toInt,
    second.toInt
  )

  def stringToDateTime(
    date: String,
    time: String
  ): Option[DateTime] = {
    s"$date $time" match {
      case rusPattern(day, month, year, hour, minute, second) =>
        Some(toDateTime(day, month, year, hour, minute, second))
      case usaPattern(month, day, year, hour, minute, second) =>
        Some(toDateTime(day, month, year, hour, minute, second))
      case engPattern(day, month, year, hour, minute, second) =>
        Some(toDateTime(day, month, year, hour, minute, second))
      case itaPattern(day, month, year, hour, minute, second) =>
        Some(toDateTime(day, month, year, hour, minute, second))
      case chePattern(day, month, year, hour, minute, second) =>
        Some(toDateTime(day, month, year, hour, minute, second))
      case swePattern(year, month, day, hour, minute, second) =>
        Some(toDateTime(day, month, year, hour, minute, second))
      case svnPattern(year, month, day, hour, minute, second) =>
        Some(toDateTime(day, month, year, hour, minute, second))
      case _ => Option.empty
    }
  }

  def dateTimeToString(
    d: DateTime,
    format: DateFormat
  ): String = {
    val formattedDay = toStandardSize(d.day)
    val formattedMonth = toStandardSize(d.month)
    val formattedHour = toStandardSize(d.hour)
    val formattedMinute = toStandardSize(d.minute)
    val formattedSecond = toStandardSize(d.second)
    format match {
      case RUS => s"${formattedDay}.${formattedMonth}.${d.year} ${formattedHour}:${formattedMinute}:${formattedSecond}"
      case USA => s"${formattedMonth}-${formattedDay}-${d.year} ${formattedHour}:${formattedMinute}:${formattedSecond}"
      case ENG => s"${formattedDay}-${formattedMonth}-${d.year} ${formattedHour}:${formattedMinute}:${formattedSecond}"
      case ITA => s"${formattedDay}/${formattedMonth}/${d.year} ${formattedHour}.${formattedMinute}.${formattedSecond}"
      case CHE => s"${formattedDay}.${formattedMonth}.${d.year} ${formattedHour},${formattedMinute},${formattedSecond}"
      case SWE => s"${d.year}-${formattedMonth}-${formattedDay} ${formattedHour}.${formattedMinute}.${formattedSecond}"
      case SVN => s"${d.year}-${formattedMonth}-${formattedDay} ${formattedHour}:${formattedMinute}:${formattedSecond}"
      case _   => s""
    }
  }

  private def toStandardSize(number: Int): String = if (number < 10) {
    s"0${number}"
  } else {
    number.toString
  }
}
