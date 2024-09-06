package collection.queue

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class QueueSpec extends AnyFlatSpec with Matchers {
  "Queue empty" should "init" in {
    Queue.empty[Nothing] shouldBe Queue.empty[Nothing]
  }

  "Queue peek" should "return None for empty queue" in {
    Queue.empty[Int].peek shouldBe None
  }

  "Queue dequeue" should "return same queue for empty queue" in {
    Queue.empty[Int].dequeue shouldBe Queue.empty[Int]
  }

  it should "work correctly for 2 elements" in {
    Queue.empty[Int].enqueue(1).enqueue(2).dequeue.peek shouldBe Some(2)
    Queue.empty[Int].enqueue(1).dequeue.dequeue.enqueue(2).peek shouldBe Some(2)
    Queue.empty[Int].enqueue(1).enqueue(2).dequeue.dequeue.peek shouldBe None
  }

  "Queue enqueue" should "work correctly" in {
    Queue.empty[Int].enqueue(1).peek shouldBe Some(1)
  }

  "Queue enqueue" should "work correctly with negative elements" in {
    Queue.empty[Int].enqueue(-1).peek shouldBe Some(-1)
  }

  "Queue dequeue" should "work correctly for 3 elements" in {
    val seq = Seq(1, 2, 3)
    var queue = Queue.empty[Int]
    for (i <- seq) {
      queue = queue.enqueue(i)
    }
    for (i <- seq) {
      queue.peek shouldBe Some(i)
      queue = queue.dequeue
    }
  }

  "Queue peak" should "work correctly after multiple enqueues" in {
    var queue = Queue.empty[Int]
    var i = 0
    while (i <= 10) {
      queue = queue.enqueue(i)
      i += 1
    }
    queue.peek shouldBe Some(0)
  }
}
