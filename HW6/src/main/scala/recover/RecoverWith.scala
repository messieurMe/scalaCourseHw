package recover

import cats.Monad

import scala.annotation.tailrec

sealed trait RecoverWith[+T] {
  def recover[B >: T](default: B): B
}

object RecoverWith {
  def some[T](value: T): RecoverWith[T] = Value(value)
  val nil: RecoverWith[Nothing]         = Empty

  private final case class Value[T](value: T) extends RecoverWith[T] { self =>
    override def recover[B >: T](default: B): B = value
  }

  private case object Empty extends RecoverWith[Nothing] {
    override def recover[B >: Nothing](default: B): B = default
  }

  implicit val monadForRecoverWith: Monad[RecoverWith] = new Monad[RecoverWith] {
    override def pure[A](x: A): RecoverWith[A] = some(x)

    override def flatMap[A, B](fa: RecoverWith[A])(f: A => RecoverWith[B]): RecoverWith[B] =
      fa match {
        case Value(value) => f(value)
        case Empty        => Empty
      }

    @tailrec
    override def tailRecM[A, B](a: A)(f: A => RecoverWith[Either[A, B]]): RecoverWith[B] = f(
      a
    ) match {
      case Empty => Empty
      case Value(value) =>
        value match {
          case Right(value) => some(value)
          case Left(value)  => tailRecM(value)(f)
        }
    }
  }
}
