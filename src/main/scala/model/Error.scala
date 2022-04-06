package model

sealed trait Error extends Throwable

object Error {
  final case class RepositoryError(cause: Throwable) extends Error
  final case class DecodingError(message: String)    extends Error
}
