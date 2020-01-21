name := "kafka-scala"

description := "kafka scala test"

scalaVersion := "2.11.3"

val akkaVersion = "2.3.2"

libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick" % "3.1.0",
  "org.slf4j" % "slf4j-nop" % "1.6.4",
  "postgresql" % "postgresql" % "9.1-901.jdbc4",
  "com.googlecode.flyway" % "flyway-core" % "2.1.1",
  "c3p0" % "c3p0" % "0.9.0.4",
  "com.github.sstone" %% "amqp-client" % "1.5",
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion % "test"
)

libraryDependencies += "org.apache.kafka" %% "kafka" % "2.1.0"
libraryDependencies += "org.apache.kafka" %% "kafka-streams-scala" % "2.1.0"
libraryDependencies += "com.typesafe.akka" %% "akka-stream-kafka" % "2.0.0"