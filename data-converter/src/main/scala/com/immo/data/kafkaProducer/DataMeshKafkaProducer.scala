package com.immo.data.kafkaProducer

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives.complete
import akka.http.scaladsl.server.StandardRoute
import com.amazonaws.services.schemaregistry.utils.AWSSchemaRegistryConstants
import com.immo.data.converters.{ConverterFactory, SingleRequestConverter}
import com.immo.data.{producer, topic}
import com.typesafe.config.Config
import org.apache.kafka.clients.producer.ProducerRecord
import org.slf4j.{Logger, LoggerFactory}

import java.util.Properties

object DataMeshKafkaProducer {

  private val logger: Logger = LoggerFactory.getLogger(getClass)

  def processAndProduceFile(filePath: String): StandardRoute = {
    val converter = ConverterFactory.getConverter(filePath)

    if(converter == null)
      return complete(StatusCodes.InternalServerError, s"Unsupported file type")

    val avroRecords = converter.convert(filePath)

    try {
      avroRecords.par.foreach {
        avroRecord =>
          val producerRecord = new ProducerRecord[String, String](topic, avroRecord.toString)
          producer.send(producerRecord)
      }
    } catch {
      case e: Exception =>
        logger.error(s"Exception occurred while pushing data in kafka - ${e.getMessage}")
        complete(StatusCodes.InternalServerError, s"Exception occurred while pushing data in kafka - ${e.getMessage}")
    }

    complete(StatusCodes.OK, s"Produced ${avroRecords.size} records in kafka")
  }

  def processAndProducerSingleReq(dataRow: String): StandardRoute = {
    val converter = new SingleRequestConverter
    val avroRecord = try {
      converter.convert(dataRow)
    } catch {
      case e: Exception =>
        logger.error(s"Error while converting base64 encoded json to avro record - ${e.getMessage}")
        return complete(StatusCodes.InternalServerError, s"Unsupported base64 string")
    }

    try {
      val producerRecord = new ProducerRecord[String, String](topic, avroRecord.toString)
      producer.send(producerRecord)
    } catch {
      case e: Exception =>
        logger.error(s"Exception occurred while pushing data in kafka - ${e.getMessage}")
        complete(StatusCodes.InternalServerError, s"Exception occurred while pushing data in kafka - ${e.getMessage}")
    }

    complete(StatusCodes.OK, s"Produced 1 record in kafka")
  }

  def resolveProducerProperties(conf : Config) : Properties = {
    val kafkaProperties = new Properties()

    kafkaProperties.put("bootstrap.servers", conf.getString("kafka.bootstrap.servers"))
    kafkaProperties.put("key.serializer", conf.getString("kafka.key.serializer.class"))
    kafkaProperties.put("value.serializer", conf.getString("kafka.value.serializer.class"))

    if(conf.getString("kafka.aws.glue.enabled").equals("true")) {
      val awsGlue = conf.getConfig("kafka.aws.glue")
      kafkaProperties.put(AWSSchemaRegistryConstants.AWS_REGION, awsGlue.getString("region"))
      kafkaProperties.put(AWSSchemaRegistryConstants.REGISTRY_NAME, awsGlue.getString("registry.name"))
      kafkaProperties.put(AWSSchemaRegistryConstants.SCHEMA_NAME, awsGlue.getString("schema.name"))
      kafkaProperties.put(AWSSchemaRegistryConstants.SCHEMA_AUTO_REGISTRATION_SETTING, awsGlue.getString("autoRegistration"))
    }

    conf.getString("kafka.provider") match {
      case "msk" =>
        val mskConfig = conf.getConfig("kafka.mskConfig")
        kafkaProperties.put("security.protocol", mskConfig.getString("security.protocol"))
        kafkaProperties.put("sasl.mechanism", mskConfig.getString("sasl.mechanism"))
        kafkaProperties.put("ssl.truststore.location", mskConfig.getString("ssl.truststore.location"))
      case "confluent" =>
        val confluentConfig = conf.getConfig("kafka.confluentConfig")
        kafkaProperties.put("sasl.jaas.config", confluentConfig.getString("sasl.jaas.config"))
        kafkaProperties.put("security.protocol", confluentConfig.getString("security.protocol"))
        kafkaProperties.put("ssl.endpoint.identification.algorithm", confluentConfig.getString("ssl.endpoint.identification.algorithm"))
        kafkaProperties.put("sasl.mechanism", confluentConfig.getString("sasl.mechanism"))
      case _ =>
        // Do nothing
    }
    
    kafkaProperties
  }
}
