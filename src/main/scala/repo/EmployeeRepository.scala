package repo

import model.Employee
import model.Error.RepositoryError
import zio._
import zio.stream._

import java.util.UUID

trait EmployeeRepository {
  def findById(id: UUID): ZIO[Any, RepositoryError, Employee]
  def updateById(employee: Employee): ZIO[Any, RepositoryError, Employee]
  def deleteById(id: UUID): ZIO[Any, RepositoryError, Int]
  def add(employee: Employee): ZIO[Any, RepositoryError, Unit]
}
object EmployeeRepository {

  def findById(id: UUID): ZIO[EmployeeRepository, RepositoryError, Employee] =
    ZIO.serviceWithZIO[EmployeeRepository](_.findById(id))

  def updateById(
      employee: Employee
  ): ZIO[EmployeeRepository, RepositoryError, Employee] =
    ZIO.serviceWithZIO[EmployeeRepository](_.updateById(employee))

  def deleteById(id: UUID): ZIO[EmployeeRepository, RepositoryError, Int] =
    ZIO.serviceWithZIO[EmployeeRepository](_.deleteById(id))

  def add(
      employee: Employee
  ): ZIO[EmployeeRepository, RepositoryError, Unit] =
    ZIO.serviceWithZIO[EmployeeRepository](_.add(employee))
}
