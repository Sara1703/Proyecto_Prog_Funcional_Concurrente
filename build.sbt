val scala3Version = "2.13.12"

// Opciones del compilador de la imagen
scalacOptions ++= Seq("-language:implicitConversions", "-deprecation")

lazy val root = project
  .in(file("."))
  .settings(
    name := "proyecto_prog_fun",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies ++= Seq(
      
      "org.scalameta" %% "munit" % "1.3.2" % Test,
      
      
      "org.scala-lang.modules" %% "scala-parallel-collections" % "1.0.4",
      
      
      ("com.storm-enroute" %% "scalameter-core" % "0.21").cross(CrossVersion.for3Use2_13),

      ("org.plotly-scala" %% "plotly-render" % "0.8.2").cross(CrossVersion.for3Use2_13)
    )
  )