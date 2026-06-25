val scala3Version = "2.13.1"

lazy val root = project
  .in(file("."))
  .settings(
    name := "proyecto_prog_fun",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies += "org.scalameta" %% "munit" % "1.3.2" % Test
  )
