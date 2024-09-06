package collection.queue

import collection.stack.Stack

import scala.annotation.tailrec

case class QueueImpl[+T](
  leftStack: Stack[T],
  rightStack: Stack[T]
) extends Queue[T] {

  /** Добавить элемент в конец очереди.
    *
    * @param value
    *   добавляемый элемент.
    * @return
    *   очередь с добавленным элементом.
    */
  override def enqueue[A >: T](value: A): Queue[A] = QueueImpl(leftStack.push(value), rightStack)

  /** Удалить элемент из начала очереди. Если очередь пуста, то возвращается очередь без изменений.
    *
    * @return
    *   очередь без первого элемента.
    */
  override def dequeue: Queue[T] = {
    if (rightStack.peek.isEmpty) {
      QueueImpl(
        Stack.empty,
        popAndPush(leftStack, rightStack).drop
      )
    } else {
      QueueImpl(
        leftStack,
        rightStack.drop
      )
    }
  }

  @tailrec private def popAndPush[A >: T](from: Stack[A], to: Stack[A]): Stack[A] = {
    from.peek match {
      case None        => to
      case Some(value) => popAndPush[A](from.drop, to.push(value))
    }
  }

  /** Получить первый элемент очереди.
    *
    * @return
    *   первый элемент очереди или None, если очередь пуста.
    */
  override def peek: Option[T] = if (rightStack.peek.isEmpty) {
    popAndPush(leftStack, rightStack).peek
  } else {
    rightStack.peek
  }
}
