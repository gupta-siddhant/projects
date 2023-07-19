package com.immo

import com.immo.data.kafkaProducer.Schema
import com.sksamuel.avro4s.RecordFormat
import com.typesafe.config.ConfigFactory
import org.apache.kafka.clients.producer.KafkaProducer

import java.util.Properties

package object data {
  private val conf = ConfigFactory.load()

  val formatter: RecordFormat[Schema] = RecordFormat[Schema]

  private val kafkaProperties = new Properties()

  kafkaProperties.put("bootstrap.servers", conf.getString("kafka.bootstrap.servers"))
  kafkaProperties.put("key.serializer", conf.getString("kafka.key.serializer.class"))
  kafkaProperties.put("value.serializer", conf.getString("kafka.value.serializer.class"))
  //kafkaProperties.put("schema.registry.url", conf.getString("kafka.schema.registry.url"))

  val topic: String = conf.getString("kafka.topic.name")
  val producer = new KafkaProducer[String, String](kafkaProperties)
}
