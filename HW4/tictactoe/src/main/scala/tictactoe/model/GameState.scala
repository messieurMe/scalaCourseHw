package tictactoe.model

import tictactoe.model.Mark.O

// Реализуйте свой класс для хранения состояния игры в крестики-нолики.
case class GameState(
    state: List[List[Mark]]
):
  // Для вывода состояния игры в консоль

  override def toString: String = {
    val sb = StringBuilder()
    state.foreach(i =>
      i.foreach(j =>
        sb.append(if (j == Mark.Empty) "-" else j)
        sb.append(" ")
      )
      sb.append("\n")
    )
    sb.toString()
  }

object GameState:

  def fromList(list: List[List[Mark]]): GameState = GameState(list)
  def empty: GameState = GameState(List.tabulate[Mark](3, 3)((_, _) => Mark.Empty))
