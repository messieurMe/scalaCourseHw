package tictactoe

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import tictactoe.error.{MoveAfterGameEnded, MoveOutOfBoundsError, MoveOverridesExistingError}
import tictactoe.model.Mark.{Empty, O, X}

class GameClientSpec extends AnyFlatSpec with Matchers {
  "GameClient" should "match expected string for moves" in {
    GameClient
      .hard()
      .makeMove(0, 0) match
      case Left(value) => fail("Expected right, but got left")
      case Right(value) =>
        value.winner shouldBe Empty
        value.lastOpponentMove shouldBe Some((0, 1))
        value.state.state shouldBe List(
          List(X, O, Empty),
          List.fill(3)(Empty),
          List.fill(3)(Empty)
        )
  }
  "GameClient" should "be able to win and identify winner" in {
    val client = GameClient
      .hard()
      .makeMove(1, 1)
      .getOrElse(fail("Expected right, but got left"))
      .makeMove(2, 2)
      .getOrElse(fail("Expected right, but got left"))
      .makeMove(1, 2)
      .getOrElse(fail("Expected right, but got left"))
    client.winner shouldBe O
    client.lastOpponentMove shouldBe Some((0, 2))
    client
      .makeMove(2, 1)
      .left
      .getOrElse(fail("Expected left, but got right"))
      .message shouldBe MoveAfterGameEnded().message
  }
  "GameClient" should "return error if we duplicate move" in {
    val client = GameClient
      .hard((1, 1))
      .makeMove(1, 1)
      .left
      .getOrElse(fail("Expected left, but got right"))
      .message shouldBe MoveOverridesExistingError(1, 1).message
  }
  "GameClient" should "return error if we make move out of bounds" in {
    val client = GameClient
      .hard((1, 1))
      .makeMove(3, 3)
      .left
      .getOrElse(fail("Expected left, but got right"))
      .message shouldBe MoveOutOfBoundsError(3, "x").message
  }
}
