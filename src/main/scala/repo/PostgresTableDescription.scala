package repo

import model.Error.RepositoryError
import zio._
import zio.sql.postgresql.PostgresModule
import zio.stream._

trait PostgresTableDescription extends PostgresModule {

  import ColumnSet._

  val employees =
    (uuid("id") ++ string("name") ++ string("role"))
      .table("employees")

  val (empId, name, role) =
    employees.columns

  implicit class ZStreamSqlExt[T](zstream: ZStream[SqlDriver, Exception, T]) {
    def provideDriver(
        driver: ULayer[SqlDriver]
    ): ZStream[Any, RepositoryError, T] =
      zstream
        .tapError(e => ZIO.logError(e.getMessage()))
        .mapError(e => RepositoryError(e.getCause()))
        .provideLayer(driver)

    def findFirst(
        driver: ULayer[SqlDriver],
        id: java.util.UUID
    ): ZIO[Any, RepositoryError, T] =
      zstream.runHead.some
        .tapError {
          case None    => ZIO.unit
          case Some(e) => ZIO.logError(e.getMessage())
        }
        .mapError {
          case None =>
            RepositoryError(
              new RuntimeException(s"Order with id $id does not exists")
            )
          case Some(e) => RepositoryError(e.getCause())
        }
        .provide(driver)
  }

  implicit class ZioSqlExt[T](zio: ZIO[SqlDriver, Exception, T]) {
    def provideAndLog(driver: ULayer[SqlDriver]): ZIO[Any, RepositoryError, T] =
      zio
        .tapError(e => ZIO.logError(e.getMessage()))
        .mapError(e => RepositoryError(e.getCause()))
        .provide(driver)
  }
}
