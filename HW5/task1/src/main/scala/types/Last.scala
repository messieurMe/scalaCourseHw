package types

import cats.{Functor, Monoid, Semigroup, kernel}
import cats.implicits._

final case class Last[+T](value: Option[T])

object Last {
  implicit def dummySemigroup[A]: Semigroup[Last[A]] = (_: Last[A], y: Last[A]) => Last(y.value)

  implicit def dummyMonoid[A](implicit semigroup: Semigroup[Last[A]]): Monoid[Last[A]] =
    new Monoid[Last[A]] {
      override def empty: Last[A] = Last(None)

      override def combine(x: Last[A], y: Last[A]): Last[A] =
        if (y == empty) x else semigroup.combine(x, y)
    }
  implicit def dummyFunctor[_]: Functor[Last] = new Functor[Last] {

    override def map[A, B](fa: Last[A])(f: A => B): Last[B] = Last(fa.value.map(f))
  }
}
