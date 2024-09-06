package env

import cats.effect.std.Env
import cats.effect.{Ref, Sync}
import cats.implicits.catsSyntaxFlatMapOps

trait LocalEnv[F[_]] extends Env[F] {
  def set(name: String, value: String): F[Unit]
}

object LocalEnv {

  def apply[F[_]](implicit
    localEnv: LocalEnv[F]
  ): LocalEnv[F] = localEnv

  def fromEnv[F[_]: Ref.Make](
    env: Env[F]
  )(implicit
    F: Sync[F]
  ): F[LocalEnv[F]] =
    Ref.of(Map[String, String]()) >>=
      (localMap => F.pure(new LocalEnvImpl(env, localMap)))

}
