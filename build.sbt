val scala3Version = "3.8.4"

lazy val root = project
  .in(file("."))
  .settings(
    name := "proyecto_prog_fun",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies += "org.scalameta" %% "munit" % "1.3.2" % Test,
    
    libraryDependencies += "org.scala-lang.modules" %% "scala-parallel-collections" % "1.0.4"
  )
