package tictactoe

import tictactoe.error.{
  GameError,
  MoveAfterGameEnded,
  MoveOutOfBoundsError,
  MoveOverridesExistingError
}
import tictactoe.model.Mark.{Empty, O, X}
import tictactoe.model.{GameState, Mark}

/** Клиент для игры в крестики-нолики.
  */
trait GameClient:
  /** Сделать ход пользователя.
    *
    * @param x
    *   номер строки
    * @param y
    *   номер столбца
    * @return
    *   Если поле свободно и игра не закончена то возвращает новое состояние клиента. Если игра уже
    *   завершилась (т.е. на поле есть выигрышная комбинация), или поле занято то возвращает ошибку.
    */
  def makeMove(x: Int, y: Int): Either[GameError, GameClient]

  /** Получить текущее состояние игры (поля).
    * @return
    *   текущее состояние игры.
    */
  def state: GameState

  /** Получить последний ход компьютера.
    * @return
    *   Если это первый ход пользователя, то None. Иначе - кортеж из номера строки и столбца
    *   последнего хода компьютера.
    */
  def lastOpponentMove: Option[(Int, Int)]

  /** Получить победителя.
    * @return
    *   победитель игры (Mark.X - пользователь или Mark.O - компьютер), если он есть, иначе
    *   Mark.Empty.
    */
  def winner: Mark

object GameClient:
  def apply(
      computer: ComputerLogic,
      computerMove: (Int, Int)
  ): GameClient = {
    GameClientImpl(
      List.tabulate(3, 3)((x, y) =>
        if (x == computerMove._1 && y == computerMove._2) O else Mark.Empty
      ),
      None,
      computer,
      X
    )
  }

  def apply(computer: ComputerLogic): GameClient =
    GameClientImpl(List.tabulate(3, 3)((_, _) => Mark.Empty), None, computer, O)

  def hard(): GameClient = GameClient(ComputerLogic.hard())

  def hard(computerMove: (Int, Int)): GameClient = GameClient(ComputerLogic.hard(), computerMove)

end GameClient

class GameClientImpl(
    private val listState: List[List[Mark]],
    private val lastMove: Option[(Int, Int)],
    private val computerLogic: ComputerLogic,
    private val currentMove: Mark
) extends GameClient {

  override def makeMove(x: Int, y: Int): Either[GameError, GameClient] =
    if (x < 0 || x >= 3) {
      Left(MoveOutOfBoundsError(x, "x"))
    } else if (y < 0 || y >= 3) {
      Left(MoveOutOfBoundsError(y, "y"))
    } else if (listState(x)(y) != Mark.Empty) {
      Left(MoveOverridesExistingError(x, y))
    } else if (winner != Mark.Empty || !listState.exists(x => x.contains(Mark.Empty))) {
      Left(MoveAfterGameEnded())
    } else {
      val newState = listState.updated(x, listState(x).updated(y, Mark.X))

      val computerMove = if (customStateWinner(newState) != Mark.Empty) {
        None
      } else {
        computerLogic.makeMove(GameState(newState)) match
          case Some(value) => Some(value)
          case None        => None
      }

      Right(
        GameClientImpl(
          computerMove match
            case Some(value) =>
              newState.updated(value._1, newState(value._1).updated(value._2, Mark.O))
            case None => newState
          ,
          computerMove match
            case Some(value) => Some(value)
            case None        => lastMove
          ,
          computerLogic,
          currentMove match
            case Mark.X     => Mark.O
            case Mark.O     => Mark.X
            case Mark.Empty => Mark.Empty,
        )
      )
    }

  override def state: GameState = GameState.fromList(listState)

  override def lastOpponentMove: Option[(Int, Int)] = lastMove

  override def winner: Mark = customStateWinner(listState)

  private def customStateWinner(list: List[List[Mark]]) =
    Seq(
      findWinner((0, 0), (1, 1), (2, 2), list),
      findWinner((0, 2), (1, 1), (2, 0), list),
      findWinner((0, 0), (0, 1), (0, 2), list),
      findWinner((1, 0), (1, 1), (1, 2), list),
      findWinner((2, 0), (2, 1), (2, 2), list),
      findWinner((0, 0), (1, 0), (2, 0), list),
      findWinner((0, 1), (1, 1), (2, 1), list),
      findWinner((0, 2), (1, 2), (2, 2), list)
    )
      .find(_ != Mark.Empty)
      .getOrElse(Mark.Empty)

  private def findWinner(
      first: (Int, Int),
      second: (Int, Int),
      third: (Int, Int),
      list: List[List[Mark]] = listState
  ): Mark =
    if (
      list(first._1)(first._2) == list(second._1)(second._2) &&
      list(second._1)(second._2) == list(third._1)(third._2) &&
      list(third._1)(third._2) != Mark.Empty
    ) {
      list(first._1)(first._2)
    } else Mark.Empty
}
