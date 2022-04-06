package repo
import model.Employee
import model.Error.RepositoryError
import zio._
import zio.prelude.EqualOps
import zio.sql.ConnectionPool
import zio.stream._

import java.util.UUID

class EmployeeRepositoryImpl(pool: ConnectionPool)
    extends EmployeeRepository
    with PostgresTableDescription {

  lazy val driverLayer = ZLayer
    .make[SqlDriver](
      SqlDriver.live,
      ZLayer.succeed(pool)
    )

  override def findById(id: UUID): ZIO[Any, RepositoryError, Employee] = {
    val employee = select(empId ++ name ++ role)
      .from(employees)
      .where(empId === id)

    ZIO.logInfo(s"Query to execute findById is ${renderRead(employee)}") *>
      execute(employee.to((Employee.apply _).tupled))
        .findFirst(driverLayer, id)
  }

  override def updateById(
      employee: Employee
  ): ZIO[Any, RepositoryError, Employee] = {
    // TODO : Fix it
//    execute(update(employees)
//      .set("name", employee.name)
//      .where(empId === employee.id))
//      .provideLayer(driverLayer)
//      .mapError(e => RepositoryError(e.getCause))
    ???

    execute(
      update(employees)
      .set(name, employee.name)
      .set(role, employee.role)
      .where(empId === employee.id)
    )
      .provideLayer(driverLayer)
      .mapError(e => RepositoryError(e))
  }

  override def deleteById(id: UUID): ZIO[Any, RepositoryError, Int] = {
    execute(
      deleteFrom(employees)
        .where(empId === id)
    )
      .provideLayer(driverLayer)
      .mapError(e => RepositoryError(e.getCause))
  }

  override def add(employee: Employee): ZIO[Any, RepositoryError, Unit] = {
    val query =
      insertInto(employees)(empId ++ name ++ role)
        .values(
          (
            employee.id,
            employee.name,
            employee.role
          )
        )

    ZIO.logInfo(s"Query to insert employee is ${renderInsert(query)}") *>
      execute(query)
        .provideAndLog(driverLayer)
        .unit
  }
}

object EmployeeRepositoryImpl {
  val live: ZLayer[ConnectionPool, Throwable, EmployeeRepository] =
    (new EmployeeRepositoryImpl(_)).toLayer
}
