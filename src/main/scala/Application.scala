import api.HttpRoutes
import config.{DbConfig, ServerConfig}
import repo.{EmployeeRepository, EmployeeRepositoryImpl}
import zhttp.service.{EventLoopGroup, Server}
import zhttp.service.server.ServerChannelFactory
import zio.{Clock, ZIOAppDefault}
import zio.config.getConfig
import zio.sql.ConnectionPool
import healthcheck.HealthCheck
object Application extends ZIOAppDefault {

  def run =
    getConfig[ServerConfig]
      .map(config =>
        Server.port(config.port) ++
          Server.app(
            HttpRoutes.app ++
              HealthCheck.expose
          )
      )
      .flatMap(_.start)
      .provide(
        ServerConfig.layer,
        ServerChannelFactory.auto,
        EventLoopGroup.auto(),
        EmployeeRepositoryImpl.live,
        DbConfig.layer,
        ConnectionPool.live,
        Clock.live,
        DbConfig.connectionPoolConfig
      )
}
