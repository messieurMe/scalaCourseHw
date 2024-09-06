package tictactoe.model

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class GameStateSpec extends AnyFlatSpec with Matchers {
  "GameState" should "match expected string from seq" in {
    val seq = List(
      List(Mark.O, Mark.Empty, Mark.Empty),
      List(Mark.Empty, Mark.X, Mark.Empty),
      List(Mark.Empty, Mark.Empty, Mark.O)
    )
    GameState.fromList(seq).toString shouldBe
      "O - - \n- X - \n- - O \n"
  }
  "GameState" should "correctly render empty state" in {
    GameState.empty.toString shouldBe "- - - \n- - - \n- - - \n"
  }
}
