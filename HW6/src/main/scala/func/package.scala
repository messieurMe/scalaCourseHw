import cats.Monad

import scala.annotation.tailrec

package object func {

  implicit def monadForFunction[T]: Monad[Function1[T, *]] = new Monad[Function1[T, *]] {
    override def pure[A](x: A): Function1[T, A] = _ => x

    override def flatMap[A, B](fa: Function1[T, A])(f: A => Function1[T, B]): Function1[T, B] =
      arg => f(fa(arg))(arg)

    override def tailRecM[A, B](a: A)(f: A => Function1[T, Either[A, B]]): Function1[T, B] =
      arg => tailRecInt(a, f, arg)

    @tailrec def tailRecInt[A, B](a: A, f: A => Function1[T, Either[A, B]], arg: T): B =
      f(a)(arg) match {
        case Right(value) => value
        case Left(value)  => tailRecInt(value, f, arg)
      }

  }
}
