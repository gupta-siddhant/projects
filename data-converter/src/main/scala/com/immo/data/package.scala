package com.immo

import com.immo.data.kafkaProducer.DataMeshKafkaProducer.resolveProducerProperties
import com.immo.data.kafkaProducer.Schema
import com.sksamuel.avro4s.RecordFormat
import com.typesafe.config.ConfigFactory
import org.apache.kafka.clients.producer.KafkaProducer

package object data {
  private val conf = ConfigFactory.load()

  val formatter: RecordFormat[Schema] = RecordFormat[Schema]

  private val kafkaProperties = resolveProducerProperties(conf)

  val topic: String = conf.getString("kafka.topic.name")

  val producer = new KafkaProducer[String, String](kafkaProperties)
}
