name := "fs2-guide"

version := "0.1"

scalaVersion := "2.12.6"

val testZv = "0.0.5"

libraryDependencies ++= Seq(
  "org.scalaz" %% "testz-core" % testZv,
  "org.scalaz" %% "testz-stdlib" % testZv,
  "co.fs2" %% "fs2-core" % "1.0.0-M5",
)

scalacOptions ++= Seq(
  "-encoding", "UTF-8",
  "-deprecation",
  "-unchecked",
  "-feature",
  "-language:higherKinds",
  "-Xlint",
  "-Xfatal-warnings",
  "-Ypartial-unification"
)