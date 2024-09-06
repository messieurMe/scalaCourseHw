package types

import cats.implicits.catsSyntaxSemigroup
import cats.{Functor, Monoid, Semigroup}

import scala.::

final case class Point[+T](x: T, y: T, z: T)

object Point {
  implicit def dummySemigroup[A: Semigroup]: Semigroup[Point[A]] = (x: Point[A], y: Point[A]) =>
    Point(x.x |+| y.x, x.y |+| y.y, x.z |+| y.z)
  implicit def dummyMonoid[A: Monoid](implicit
      nestedMonoid: Monoid[A],
      semigroup: Semigroup[Point[A]]
  ): Monoid[Point[A]] =
    new Monoid[Point[A]] {
      override def empty: Point[A] =
        Point(nestedMonoid.empty, nestedMonoid.empty, nestedMonoid.empty)

      override def combine(x: Point[A], y: Point[A]): Point[A] = semigroup.combine(x, y)
    }
  implicit def dummyFunctor[_]: Functor[Point] = new Functor[Point] {
    override def map[A, B](fa: Point[A])(f: A => B): Point[B] = Point(f(fa.x), f(fa.y), f(fa.z))
  }
}
