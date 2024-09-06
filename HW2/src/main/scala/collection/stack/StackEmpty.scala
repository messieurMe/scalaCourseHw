package collection.stack

private case object StackEmpty extends Stack[Nothing] {
  override def push[A >: Nothing](value: A): Stack[A] =
    StackImpl(List(value))

  override def peek: Option[Nothing] = None

  override def drop: Stack[Nothing] = StackEmpty
}
