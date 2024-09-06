package collection.stack

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class StackSpec extends AnyFlatSpec with Matchers {
  "Stack empty" should "init" in {
    Stack.empty[Nothing] shouldBe Stack.empty[Nothing]
  }

  "Stack peek" should "return None for empty queue" in {
    Stack.empty[Int].peek shouldBe None
  }

  "Stack drop" should "return same queue for empty queue" in {
    Stack.empty[Int].drop shouldBe Stack.empty[Int]
  }

  it should "work correctly for 2 elements" in {
    Stack.empty[Int].push(1).push(2).drop.peek shouldBe Some(1)
    Stack.empty[Int].push(1).drop.drop.push(2).peek shouldBe Some(2)
    Stack.empty[Int].push(1).push(2).drop.drop.peek shouldBe None
  }

  "Stack push" should "work correctly" in {
    Stack.empty[Int].push(1).peek shouldBe Some(1)
  }
}
