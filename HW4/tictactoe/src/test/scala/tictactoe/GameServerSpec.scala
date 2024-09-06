package tictactoe

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import tictactoe.model.Mark.Empty
import tictactoe.model.{GameState, Mark}

class GameServerSpec extends AnyFlatSpec with Matchers {
  "GameServer" should "call computer move first if he's first" in {
    // First move is (2, 2) instead of (0, 0) because that's how it defined in GameServer
    GameServer.computerFirst().startGame().state.toString shouldBe "- - - \n- - - \n- - O \n"
  }
  "GameServer" should "not call computer move first player is first" in {
    GameServer.userFirst().startGame().state.toString shouldBe "- - - \n- - - \n- - - \n"
  }
  "GameServer" should "be random if we choose random" in {
    GameServerImpl(GameClient.hard())
      .startGame()
      .winner shouldBe Mark.Empty

    GameServer.random(1).startGame().state.toString shouldBe "- - - \n- - - \n- - O \n"
    GameServer.random(2).startGame().state.toString shouldBe "- - - \n- - - \n- - - \n"
  }
}
