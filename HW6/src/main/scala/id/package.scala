import cats.Monad

import scala.annotation.tailrec

package object id {
  type MyId[A] = A

  object MyId {
    def apply[A](a: A): MyId[A] = a
  }

  implicit val monadForMyId: Monad[MyId] = new Monad[MyId] {
    override def pure[A](x: A): MyId[A] = MyId.apply(x)

    override def flatMap[A, B](fa: MyId[A])(f: A => MyId[B]): MyId[B] = f(fa)

    @tailrec
    override def tailRecM[A, B](a: A)(f: A => MyId[Either[A, B]]): MyId[B] = f(a) match {
      case Right(value) => value
      case Left(value)  => tailRecM(value)(f)
    }
  }
}
