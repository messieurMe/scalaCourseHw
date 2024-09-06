package collection.queue

import collection.stack.Stack

trait Queue[+T] {

  /** Добавить элемент в конец очереди.
    * @param value
    *   добавляемый элемент.
    * @return
    *   очередь с добавленным элементом.
    */
  def enqueue[A >: T](value: A): Queue[A]

  /** Удалить элемент из начала очереди. Если очередь пуста, то возвращается очередь без изменений.
    * @return
    *   очередь без первого элемента.
    */
  def dequeue: Queue[T]

  /** Получить первый элемент очереди.
    * @return
    *   первый элемент очереди или None, если очередь пуста.
    */
  def peek: Option[T]
}

object Queue {
  def empty[T]: Queue[T] = QueueImpl(Stack.empty, Stack.empty)
}
