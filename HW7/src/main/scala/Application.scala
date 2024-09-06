import cats.FlatMap
import cats.effect.std.Console
import cats.effect.{ExitCode, IO, IOApp, Sync}
import cats.syntax.all._
import env.LocalEnv

object Application extends IOApp {

  // $COVERAGE-OFF$
  override def run(args: List[String]): IO[ExitCode] =
    helloWorld[IO]().as(ExitCode.Success)

  def helloWorld[F[_]: Console: FlatMap](): F[Unit] =
    for {
      _    <- Console[F].println("What your name?")
      name <- Console[F].readLine
      _    <- Console[F].println(s"Hello $name!")
    } yield ()
  // $COVERAGE-ON$

  // Вам требуется реализовать данную функцию
  def consoleApplication[F[_]](implicit
    sync: Sync[F],
    console: Console[F],
    localEnv: LocalEnv[F]
  ): F[Unit] = {
    for {
      input <- console.readLine
      _ <- {
        val inputCommand = input.split(" +").toList
        val command = inputCommand.get(0) match {
          case Some(value) => value
          case None        => "exit"
        }
        val key = inputCommand.get(1) match {
          case Some(value) => value
          case None        => ""
        }
        val value = inputCommand.get(2) match {
          case Some(value) => value
          case None        => ""
        }
        command match {
          case "get" =>
            localEnv.get(key) >>=
              ((result: Option[String]) =>
                result match {
                  case Some(value) => console.println(s"'$key' '$value'")
                  case None        => console.println("Not found")
                }
              )
          case "set" =>
            localEnv.set(key, value) >>=
              (_ => console.println(s"'$key' '$value'"))
          case _ => console.pure
        }
      }
    } yield input
  }.iterateWhile(input => input != "exit")
    .map(_ => ())

}
