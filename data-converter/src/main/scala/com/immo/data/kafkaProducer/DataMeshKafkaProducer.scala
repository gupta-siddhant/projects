package com.immo.data.kafkaProducer

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives.complete
import akka.http.scaladsl.server.StandardRoute
import com.immo.data.converters.{ConverterFactory, SingleRequestConverter}
import com.immo.data.{producer, topic}
import org.apache.kafka.clients.producer.ProducerRecord
import org.slf4j.{Logger, LoggerFactory}

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

}
