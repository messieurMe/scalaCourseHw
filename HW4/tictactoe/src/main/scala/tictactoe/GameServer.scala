package tictactoe

import scala.util.Random

/** GameServer создаёт клиента для игры в крестики нолики. А так же задаёт право первого хода и
  * сложность оппонента.
  */
trait GameServer:
  def startGame(): GameClient

// Вы можете изменить текущие или добавить свои конструкторы.
object GameServer:
  def userFirst(): GameServer = GameServerImpl(GameClient.hard())

  def computerFirst(): GameServer =
    GameServerImpl(GameClient.hard((2, 2))) // Вы можете добавить свою эвристику

  def random(seed: Int = Random.nextInt()): GameServer =
    if (seed % 2 == 0) userFirst() else computerFirst()
end GameServer

class GameServerImpl(client: GameClient) extends GameServer {
  override def startGame(): GameClient = client
}
