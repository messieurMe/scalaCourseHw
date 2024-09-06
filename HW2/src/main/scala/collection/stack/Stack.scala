package collection.stack

trait Stack[+T] {
  def push[A >: T](value: A): Stack[A]
  def peek: Option[T]
  def drop: Stack[T]
}

object Stack {
  def empty[T]: Stack[T] =
    StackEmpty
}
