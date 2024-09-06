package date

import date.DateFormat._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ConverterSpec extends AnyFlatSpec with Matchers {
  "Converter stringToDateTime" should "correctly work for USA format" in {
    Converter.stringToDateTime("01-26-1993", "18:24:00") shouldBe Some(DateTime(26, 1, 1993, 18, 24, 0))
    Converter.stringToDateTime("01-26-1993", "18.24.00") shouldBe None
  }

  it should "correctly work for SWE format" in {
    Converter.stringToDateTime("1993-01-26", "18.24.00") shouldBe Some(DateTime(26, 1, 1993, 18, 24, 0))
    Converter.stringToDateTime("1993-01-26", "18,24,00") shouldBe None
  }

  it should "correctly work for SVN format" in {
    Converter.stringToDateTime("1993-01-26", "18:24:00") shouldBe Some(DateTime(26, 1, 1993, 18, 24, 0))
    Converter.stringToDateTime("1993-01-26", "18,24,00") shouldBe None
  }

  "Converter dateTimeToString" should "correctly work for USA format" in {
    Converter.dateTimeToString(exampleDate, USA) shouldBe "01-26-1993 18:24:00"
  }

  it should "correctly work for SVN format" in {
    Converter.dateTimeToString(exampleDate, SVN) shouldBe "1993-01-26 18:24:00"
  }

  it should "correctly work for RUS format" in {
    Converter.dateTimeToString(exampleDate, RUS) shouldBe "26.01.1993 18:24:00"
  }

  it should "correctly work for ENG format" in {
    Converter.dateTimeToString(exampleDate, ENG) shouldBe "26-01-1993 18:24:00"
  }

  it should "correctly work for CHE format" in {
    Converter.dateTimeToString(exampleDate, CHE) shouldBe "26.01.1993 18,24,00"
  }

  private lazy val exampleDate = DateTime(26, 1, 1993, 18, 24, 0)
}
