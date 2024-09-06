package tictactoe.error

// Реализуйте свою иерархию ошибок, которые могут возникнуть в программе.
trait GameError {
  val message: String
}

class MoveOutOfBoundsError(value: Int, dimension: String) extends GameError {
  override val message: String =
    s"Allowed bounds [0, 2]; '$value' is out of bounds for '$dimension''"
}

class MoveOverridesExistingError(x: Int, y: Int) extends GameError {
  override val message: String = s"Point ($x, $y) is already occupied"
}

class MoveAfterGameEnded extends GameError {
  override val message: String = "Game already ended. Cannot make move"
}
