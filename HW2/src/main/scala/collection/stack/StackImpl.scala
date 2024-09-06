package collection.stack

private case class StackImpl[+T](
  list: List[T]
) extends Stack[T] {
  override def push[A >: T](value: A): Stack[A] =
    StackImpl(value +: list)

  override def peek: Option[T] =
    list.headOption

  override def drop: Stack[T] = list match {
    case _ :: tail => StackImpl(tail)
    case Nil       => StackEmpty
  }
}
