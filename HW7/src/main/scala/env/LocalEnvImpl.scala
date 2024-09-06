package env

import cats.effect.std.Env
import cats.effect.{Ref, Sync}
import cats.implicits.catsSyntaxFlatMapOps

import scala.collection.immutable.Iterable

// Добавьте сюда реализацию
private class LocalEnvImpl[F[_]](
  env: Env[F],
  localEnv: Ref[F, Map[String, String]]
)(implicit
  F: Sync[F]
) extends LocalEnv[F] {

  override def set(name: String, value: String): F[Unit] =
    localEnv.update(map => map + (name -> value))

  override def get(name: String): F[Option[String]] =
    localEnv.get >>=
      (envMap =>
        envMap.get(name) match {
          case localResult @ Some(_) => F.pure(localResult)
          case None                  => env.get(name)
        }
      )

  override def entries: F[Iterable[(String, String)]] =
    localEnv.get >>=
      (localEntries =>
        env.entries >>=
          (sysEntries => F.pure(localEntries ++ sysEntries))
      )

}
