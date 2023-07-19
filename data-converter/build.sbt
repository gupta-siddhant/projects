ThisBuild / version := "1.0.0"

ThisBuild / scalaVersion := "2.12.18"

lazy val root = (project in file("."))
  .settings(
    name := "data-converter"
  )

libraryDependencies += "com.typesafe" % "config" % "1.4.2"
libraryDependencies += "com.sksamuel.avro4s" %% "avro4s-core" % "4.1.0"
libraryDependencies += "com.amazonaws" % "aws-java-sdk-s3" % "1.12.470"
libraryDependencies += "org.apache.commons" % "commons-csv" % "1.10.0"
libraryDependencies += "com.typesafe.play" %% "play-json" % "2.9.4"
libraryDependencies += "org.apache.kafka" % "kafka-clients" % "3.5.0"
libraryDependencies += "software.amazon.glue" % "schema-registry-serde" % "1.1.15"
libraryDependencies += "org.slf4j" % "slf4j-api" % "2.0.7"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.4.8"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http"   % "10.5.2",
  "com.typesafe.akka" %% "akka-stream" % "2.8.3",
  "com.typesafe.akka" %% "akka-actor"  % "2.8.3"
)


