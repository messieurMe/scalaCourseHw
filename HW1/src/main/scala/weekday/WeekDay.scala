package weekday

sealed trait WeekDay {}

case object Monday extends WeekDay
case object Tuesday extends WeekDay
case object Wednesday extends WeekDay
case object Thursday extends WeekDay
case object Friday extends WeekDay
case object Saturday extends WeekDay
case object Sunday extends WeekDay

object WeekDay {

  private val weekDays = List(Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday)

  def nextDay(day: WeekDay): WeekDay = weekDays((weekDays.indexOf(day) + 1) % 7)
  def prevDay(day: WeekDay): WeekDay = weekDays((weekDays.indexOf(day) + 6) % 7)
  def isWeekend(day: WeekDay): Boolean = day == Saturday || day == Sunday
  def isWeekday(day: WeekDay): Boolean = !isWeekend(day)
  def daysToWeekend(day: WeekDay): Int = day match {
    case Sunday | Saturday => 0
    case _                 => 5 - weekDays.indexOf(day)
  }
  def daysBetween(from: WeekDay, to: WeekDay): Int = (7 + weekDays.indexOf(to) - weekDays.indexOf(from)) % 7
  def fromInt(day: Int): WeekDay = weekDays((day % 7 + 7) % 7)
  def toInt(day: WeekDay): Int = weekDays.indexOf(day)
  def fromNameOpt(day: String): Option[WeekDay] = weekDays.find(it => it.toString == day)
  def name(day: WeekDay): String = day.toString
}
