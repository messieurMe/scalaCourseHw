package env

import cats.Functor
import cats.implicits.toFunctorOps

// Разрешается менять данный файл как угодно

sealed trait Env[F[_]] {

  def getEnv(key: F[_]): F[Option[String]]
}

object Env {

  implicit final class EnvMapSyntax(val self: Map[String, String]) extends AnyVal {
    def toEnv[F[_]: Functor]: Env[F] = new MapEnv[F](self)
  }

  def stub[F[_]: Functor]: Env[F] = new StubEnv()

  def env[F[_]: Functor]: Env[F] = new EnvEnv[F]()
}

class StubEnv[F[_]: Functor] extends Env[F] {

  override def getEnv(key: F[_]): F[Option[String]] = key.map(_ => Some("scala"))
}

class MapEnv[F[_]: Functor](val dict: Map[String, String]) extends Env[F] {

  override def getEnv(key: F[_]): F[Option[String]] = key.map {
    case x: String => dict.get(x)
    case _         => None
  }
}

class EnvEnv[F[_]: Functor] extends Env[F] {

  override def getEnv(key: F[_]): F[Option[String]] = key.map {
    case x: String => sys.env.get(x)
    case _         => None
  }
}
