import scala.annotation.tailrec

// Use @tailrec annotation to make tail recursive function
package object recursion {

  private val one = BigInt.apply(1)
  private val zero = BigInt.apply(0)
  def factorial(n: Int): Option[BigInt] = {

    @tailrec def factorial(x: BigInt, acc: BigInt): BigInt = if (x == zero) {
      one
    } else if (x == one) {
      acc
    } else {
      factorial(x.-(1), acc.*(x))
    }

    if (n < 0) Option.empty else Some(factorial(BigInt.apply(n), one))
  }

  def fibonacci(n: Int): Option[BigInt] = {
    @tailrec def fibonacci(x: BigInt, prev: BigInt, current: BigInt): BigInt = if (x == 0) {
      prev
    } else if (x == 1) {
      current
    } else {
      fibonacci(x.-(1), current, prev + current)
    }
    if (n < 0) {
      Option.empty
    } else {
      Some(fibonacci(BigInt.apply(n), 0, 1))
    }
  }
}
