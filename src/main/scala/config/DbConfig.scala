package config

import com.typesafe.config.ConfigFactory
import zio.config.ConfigDescriptor._
import zio.config.magnolia.DeriveConfigDescriptor.descriptor
import zio.config.read
import zio.config.typesafe.TypesafeConfigSource
import zio.sql.ConnectionPoolConfig
import zio.{ZIO, ZLayer}

import java.util.Properties

final case class DbConfig(
    host: String,
    port: String,
    dbName: String,
    url: String,
    user: String,
    password: String,
    driver: String,
    connectThreadPoolSize: Int
)

object DbConfig {

  val dbConfigDescriptor = nested("db-config")(descriptor[DbConfig])

  val layer = ZIO
    .attempt(
      TypesafeConfigSource.fromTypesafeConfig(
        ZIO.attempt(ConfigFactory.defaultApplication())
      )
    )
    .map(source => dbConfigDescriptor from source)
    .flatMap(config => read(config))
    .orDie
    .toLayer

  val connectionPoolConfig: ZLayer[DbConfig, Throwable, ConnectionPoolConfig] =
    (for {
      serverConfig <- ZIO.service[DbConfig]
    } yield (ConnectionPoolConfig(
      serverConfig.url,
      connectionProperties(serverConfig.user, serverConfig.password)
    ))).toLayer

  private def connectionProperties(
      user: String,
      password: String
  ): Properties = {
    val props = new Properties
    props.setProperty("user", user)
    props.setProperty("password", password)
    props
  }
}
