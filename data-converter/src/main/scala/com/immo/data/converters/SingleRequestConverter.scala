package com.immo.data.converters

import com.immo.data.kafkaProducer.Schema
import com.sksamuel.avro4s.{Record, RecordFormat}
import org.apache.commons.codec.binary.Base64
import play.api.libs.json.{Json, OFormat}

class SingleRequestConverter {

  implicit val schemaFormat: OFormat[Schema] = Json.format[Schema]

  def convert(dataRow: String): Record = {
    val row = new String(Base64.decodeBase64(dataRow), "UTF-8")
    val json = Json.parse(row)
    val parsedRow = json.as[Schema]

    val avroRecord = RecordFormat[Schema].to(parsedRow)

    avroRecord
  }
}
