ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

val zioHttpVersion = "2.0.0-RC4"
val zioVersion = "2.0.0-RC2"
val zioJsonVersion = "0.3.0-RC3"
val zioConfigVersion = "3.0.0-RC2"
val zioSqlVersion = "0.0.1"
val logbackVersion = "1.2.7"
val testcontainersVersion = "1.16.2"
val testcontainersScalaVersion = "0.39.12"

lazy val root = (project in file("."))
  .settings(
    name := "minimal-zio-crud-api",
    libraryDependencies ++= Seq(
      //zio
      "dev.zio" %% "zio" % zioVersion,
      //http
      "io.d11" %% "zhttp"      % zioHttpVersion,
      "io.d11" %% "zhttp-test" % zioHttpVersion % Test,
      //sql
      "dev.zio" %% "zio-sql-postgres" % zioSqlVersion,
      //config
      "dev.zio" %% "zio-config" % zioConfigVersion,
      "dev.zio" %% "zio-config-typesafe" % zioConfigVersion,
      "dev.zio" %% "zio-config-magnolia" % zioConfigVersion,
      //json
      "dev.zio" %% "zio-json" % zioJsonVersion,
      // test dependencies
      "dev.zio" %% "zio-test" % zioVersion % Test,
      "dev.zio" %% "zio-test-sbt" % zioVersion % Test,
      "dev.zio" %% "zio-test-junit" % zioVersion % Test,
      "com.dimafeng" %% "testcontainers-scala-postgresql" % testcontainersScalaVersion % Test,
      "org.testcontainers" % "testcontainers" % testcontainersVersion % Test,
      "org.testcontainers" % "database-commons" % testcontainersVersion % Test,
      "org.testcontainers" % "postgresql" % testcontainersVersion % Test,
      "org.testcontainers" % "jdbc" % testcontainersVersion % Test,
    )
  )
