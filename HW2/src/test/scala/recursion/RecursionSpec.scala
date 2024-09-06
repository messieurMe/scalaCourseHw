package recursion

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class RecursionSpec extends AnyFlatSpec with Matchers {
  "factorial" should "return None for negative number" in {
    factorial(-1) shouldBe None
    factorial(Int.MinValue) shouldBe None
  }

  it should "be one for zero" in {
    factorial(0) shouldBe Some(BigInt(1))
  }

  it should "be 3628800 for 10" in {
    factorial(10) shouldBe Some(BigInt(3628800))
  }

  it should "be None for -1" in {
    factorial(-1) shouldBe None
  }

  "fibonacci" should "return None for negative number" in {
    fibonacci(-1) shouldBe None
  }

  "fibbonaci" should "return 1 for 0" in {
    fibonacci(0) shouldBe Some(0)
  }

  "fibbonaci" should "return 2 for 1" in {
    fibonacci(1) shouldBe Some(1)
  }

  "fibbonaci" should "return 2 for 2" in {
    fibonacci(2) shouldBe Some(1)
  }

  it should "be 4181 for 19" in {
    fibonacci(19) shouldBe Some(BigInt(4181))
  }
}
