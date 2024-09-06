package tictactoe

import tictactoe.model.Mark
import scala.io.StdIn.readLine

object Application:
  /** Запускает игру в крестики-нолики. Пользовательский ввод производится через консоль.
    * Поддерживаемые команды: * q - завершить игру; * r - начать новую игру; * x y - выбрать клетку;
    * * h - вывести справку по игре;
    *
    * Пример:
    * {{{
    *  < New game
    *  < your move
    *  > 1 1
    *  < 2 2
    *  > 1 2
    *  < 1 3
    *  > show
    *  < x x o
    *  < - o -
    *  < - - -
    *  > q
    *  < Goodbye!
    * }}}
    *
    * Пример 2:
    * {{{
    *  < New game
    *  < 2 2
    *  > 2 2
    *  < Wrong move, try again
    *  > 1 1
    *  < 1 2
    *  > r
    *  < New game
    *  < your move
    *  > 1 2
    *  > q
    *  < Goodbye!
    * }}}
    */
  def main(args: Array[String]): Unit = {
    println("New game")
    var client: Option[GameClient] = Some(GameServer.random().startGame())

    var continue = true
    while continue do {
      readLine() match
        case "q" => continue = false
        case "r" => client = Some(GameServer.random().startGame()); println("New game")
        case "h" =>
          client match
            case Some(value) => println(value.state.toString)
            case None        => println("No active games")
        case other =>
          val command = other.split(" ").map(_.toIntOption)
          if (
            command.length == 2 && command(0).isDefined && command(1).isDefined && client.isDefined
          ) {
            val result = client.get.makeMove(command(0).get, command(1).get)
            result match
              case Left(value) => println(value.message)
              case Right(value) =>
                var board = value
                if (value.winner != Mark.Empty) {
                  println(s"Winner: ${value.winner}")
                  client = None
                } else {
                  client = Some(value)
                  value.lastOpponentMove match
                    case Some(value) => println(s"${value._1} ${value._2}")
                    case None        => println("Game ended")
                }
          } else {
            println("Wrong input")
          }
    }
  }
end Application
