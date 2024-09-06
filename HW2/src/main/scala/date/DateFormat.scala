package date

sealed trait DateFormat

object DateFormat {

  case object RUS extends DateFormat

  case object USA extends DateFormat

  case object ENG extends DateFormat

  case object ITA extends DateFormat

  case object CHE extends DateFormat

  case object SWE extends DateFormat

  case object SVN extends DateFormat
  case object XXX extends DateFormat

}
