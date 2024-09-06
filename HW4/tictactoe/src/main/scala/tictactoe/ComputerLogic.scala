package tictactoe

import tictactoe.model.{GameState, Mark}

/** Реализует различные эвристики игры компьютера.
  */
trait ComputerLogic:
  /** Сделать ход компьютера. Компьютер не может делать ход в поле, которое уже занято.
    * @param state
    *   Состояние игры.
    * @return
    *   Кортеж из номера строки и столбца - хода компьютера. Если ходить невозможно, то None.
    */
  def makeMove(state: GameState): Option[(Int, Int)]

object ComputerLogic:
  // hard эвристика не обязательна к реализации; за корректную реализацию бонусный балл.
  /** Эвристика, которая выбирает ход таким образом, что компьютер будет выигрывать в любой ситуации
    * (или сводить в ничью).
    * @return
    */
  def hard(): ComputerLogic = ComputerLogicImpl()
  def easy(): ComputerLogic = { ComputerLogicImpl() }
end ComputerLogic

class ComputerLogicImpl extends ComputerLogic {

  override def makeMove(state: GameState): Option[(Int, Int)] = Seq
    .tabulate(3, 3)((_, _))
    .flatten
    .find(x => state.state(x._1)(x._2) == Mark.Empty)
}
