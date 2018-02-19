name := "akkademy-db"
organization := "com.akkademy-db"
version := "0.0.1-SNAPSHOT"

scalaVersion := "2.12.4"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.5.9",
  "com.typesafe.akka" %% "akka-testkit" % "2.5.9" % "test",
  "com.typesafe.akka" %% "akka-remote" % "2.5.9",
  "junit" % "junit" % "4.11" % "test",
  "com.novocode" % "junit-interface" % "0.10" % "test",
  "org.scalatest" %% "scalatest" % "3.0.4" % "test"
)

mappings in (Compile, packageBin) ~= { _.filterNot {
  case (_, name) =>
    Seq("application.conf").contains(name)
}}
