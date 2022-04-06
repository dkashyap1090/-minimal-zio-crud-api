package healthcheck

import zio._
import zhttp.http._

object HealthCheck {
  val expose: HttpApp[Any, Throwable] = Http.collectZIO {
    case Method.GET -> _ / "health" =>
      ZIO.succeed(Response.status(Status.OK))
  }
}
