package com.example

import java.util.Properties

import akka.actor.ActorSystem
import akka.kafka.scaladsl.Consumer
import akka.kafka.{ConsumerSettings, Subscriptions}
import akka.stream.ActorMaterializer
import org.apache.kafka.clients.consumer.{ConsumerConfig, ConsumerRecord, KafkaConsumer}
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.StringDeserializer

import scala.collection.JavaConverters._


object KafkaConsumer {


  def streamConsumer() = {
    implicit val system = ActorSystem("SimpleStream")
    implicit val actorMaterializer = ActorMaterializer()

    val subscription = Subscriptions.assignment(new TopicPartition("test", 0))
    val consumerSettings = ConsumerSettings(system, new StringDeserializer, new StringDeserializer)
      .withBootstrapServers("localhost:9092")
      .withGroupId("group1")
      .withProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
    Consumer.plainSource(consumerSettings, subscription).runForeach((value: ConsumerRecord[String, String]) =>
      println(s"key ${value.key()} value ${value.value()}")

    )
  }

  def normalConsumer() = {
    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9092")
    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("auto.offset.reset", "latest")
    props.put("group.id", "consumer-group")
    val consumer: KafkaConsumer[String, String] = new KafkaConsumer[String, String](props)
    consumer.subscribe(java.util.Arrays.asList("test"))

    while (true) {
      val record = consumer.poll(1000).asScala
      for (data <- record.iterator)
        println(data.value())
    }
  }

  def main(args: Array[String]): Unit = {
    streamConsumer()
  }
}
