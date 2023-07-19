package com.immo.data.converters

import com.immo.data.kafkaProducer.Schema
import com.sksamuel.avro4s.{Record, RecordFormat}
import play.api.libs.json.{Json, OFormat}

class JSONBatchFileConverter extends BatchFileConverter {

  implicit val schemaFormat: OFormat[Schema] = Json.format[Schema]

  override def convert(filePath: String): Seq[Record] = {
    val jsonString = scala.io.Source.fromInputStream(s3InputStream(filePath)).mkString

    val json = Json.parse(jsonString)
    val dataRows = json.as[Seq[Schema]]

    val avroRecords = dataRows.map {
      person =>
        RecordFormat[Schema].to(person)
    }

    avroRecords
  }

}
