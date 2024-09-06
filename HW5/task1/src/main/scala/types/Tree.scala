package types

import cats.{Functor, Monoid, Semigroup}

sealed trait Tree[+T] {}

object Tree {
  final case class Leaf[+T](value: T) extends Tree[T] {
    override def equals(obj: Any): Boolean = obj match {
      case Leaf(_) => true
      case _       => false
    }
  }
  final case class Branch[+T](value: T, left: Tree[T], right: Tree[T]) extends Tree[T] {

    override def equals(obj: Any): Boolean = obj match {
      case Leaf(_)                  => false
      case branch @ Branch(_, _, _) => bfs(branch) == bfs(Branch(value, left, right))
    }

    private def bfs[K](tree: Tree[K]): Int = {
      tree match {
        case Leaf(_)         => 1
        case Branch(_, l, r) => 1 + bfs(l) + bfs(r)
        case Nil             => 0
      }
    }
  }
  object Nil extends Tree[Nothing] {
    override def equals(obj: Any): Boolean = toString == obj.toString

  }

  // Идея предлагает использовать лямбду и не прописывать `new Semigroup...`.
  // В 44 строке функция вызывается рекурсивно, поэтому хочу явно оверрайдить функцию
  //   noinspection ConvertExpressionToSAM
  implicit def dummySemigroup[A]: Semigroup[Tree[A]] = new Semigroup[Tree[A]] {
    override def combine(x: Tree[A], y: Tree[A]): Tree[A] = x match {
      case Leaf(xValue) =>
        y match {
          case leaf @ Leaf(_)           => Branch(xValue, Nil, leaf)
          case branch @ Branch(_, _, _) => Branch(xValue, Nil, branch)
          case Nil                      => x
        }
      case Branch(value, left, right) => Branch(value, left, combine(right, y))
      case Nil                        => y
    }
  }
  implicit def dummyMonoid[A](implicit semigroup: Semigroup[Tree[A]]): Monoid[Tree[A]] =
    new Monoid[Tree[A]] {
      override def empty: Tree[A] = Nil

      override def combine(x: Tree[A], y: Tree[A]): Tree[A] = semigroup.combine(x, y)
    }
  implicit def dummyFunctor: Functor[Tree] = new Functor[Tree] {
    override def map[A, B](fa: Tree[A])(f: A => B): Tree[B] = fa match {
      case Leaf(value)                => Leaf(f(value))
      case Branch(value, left, right) => Branch(f(value), map(left)(f), map(right)(f))
      case Nil                        => Nil
    }
  }
}
