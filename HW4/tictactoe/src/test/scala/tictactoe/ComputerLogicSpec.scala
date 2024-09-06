package tictactoe

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import tictactoe.model.Mark.Empty
import tictactoe.model.{GameState, Mark}

class ComputerLogicSpec extends AnyFlatSpec with Matchers {
  "ComputerLogic" should "be able to move in easy mode" in {
    val computerMove = ComputerLogic
      .easy()
      .makeMove(
        GameState(List.tabulate(3, 3)((x, y) => if (x == 2 && y == 2) Mark.Empty else Mark.X))
      )

    computerMove shouldBe Option(2, 2)
  }
  "ComputerLogic" should "return empty if there is no available space " in {
    val computerMove = ComputerLogic
      .easy()
      .makeMove(GameState(List.tabulate(3, 3)((_, _) => Mark.X)))

    computerMove shouldBe None
  }
}
