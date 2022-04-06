package api

import json.JsonHelper._
import model.Employee
import repo.EmployeeRepository
import zhttp.http._
import zio.ZIO
import zio.json.DecoderOps

object HttpRoutes {

  val app: HttpApp[
    EmployeeRepository,
    Throwable
  ] = Http.collectZIO {
    case Method.GET -> _ / "employee" / zhttp.http.uuid(id) =>
      EmployeeRepository
        .findById(id)
        .either
        .map {
          case Right(customer) => Response.json(customer.toString)
          case Left(e)         => Response.text(e.getMessage)
        }

    case req @ Method.PUT -> _ / "employee" =>
      (for {
        body <- req.bodyAsString
          .flatMap(request =>
            ZIO
              .fromEither(request.fromJson[Employee])
              .mapError(e => new Throwable(e))
          )
          .mapError(e => model.Error.DecodingError(e.getMessage))
          .tapError(e => ZIO.logInfo(s"Unpardonable body $e"))
        _ <- EmployeeRepository.updateById(body)
      } yield ()).either.map {
        case Right(_) => Response.status(Status.CREATED)
        case Left(_)  => Response.status(Status.BAD_REQUEST)
      }

    case Method.DELETE -> _ / "employee" / zhttp.http.uuid(id) =>
      EmployeeRepository
        .deleteById(id)
        .either
        .map {
          case Right(customer) => Response.json(customer.toString)
          case Left(e)         => Response.text(e.getMessage)
        }

    case req @ Method.POST -> _ / "employee" =>
      (for {
        body <- req.bodyAsString
          .flatMap(request =>
            ZIO
              .fromEither(request.fromJson[Employee])
              .mapError(e => new Throwable(e))
          )
          .mapError(e => model.Error.DecodingError(e.getMessage))
          .tapError(e => ZIO.logInfo(s"Unpardonable body $e"))
        _ <- EmployeeRepository.add(body)
      } yield ()).either.map {
        case Right(_) => Response.status(Status.CREATED)
        case Left(_)  => Response.status(Status.BAD_REQUEST)
      }
  }
}
