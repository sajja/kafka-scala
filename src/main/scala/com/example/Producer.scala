package com.example

import java.util.Properties

import akka.Done
import akka.actor.ActorSystem
import akka.kafka.ProducerSettings
import org.apache.kafka.clients.producer._
import org.apache.kafka.common.serialization.{ByteArraySerializer, Serdes, StringSerializer}
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.kstream.KStream
import akka.kafka.scaladsl.Producer
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Source

import scala.concurrent.Future

object KafkaProducer {

  def basic() = {
    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9092")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    val producer = new KafkaProducer[String, String](props)
    val record = new ProducerRecord[String, String]("test", "key", "value")
    val f = producer.send(record)

    while (true) {
      producer.send(record)

    }

    producer.close()
  }


  def stream() = {
    implicit val actorSystem = ActorSystem("SimpleStream")
    implicit val actorMaterializer = ActorMaterializer()

    val producerSettings = ProducerSettings(actorSystem, new StringSerializer, new StringSerializer).withBootstrapServers("localhost:9092")
    val infiniteSource = Source.tick()
    val done: Future[Done] =
      Source(1 to 100)
        .map(_.toString)
        .map(value => new ProducerRecord[String, String]("test", "Key", "value..............."))
        .runWith(Producer.plainSink(producerSettings))
  }


  def main(args: Array[String]): Unit = {
    stream()

  }

}
