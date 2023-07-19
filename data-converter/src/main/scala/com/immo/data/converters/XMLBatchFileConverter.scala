package com.immo.data.converters


import com.immo.data.kafkaProducer.Schema
import com.sksamuel.avro4s.{Record, RecordFormat}

import scala.xml.XML

class XMLBatchFileConverter extends BatchFileConverter {

  override def convert(filePath: String): Seq[Record] = {
    val xmlData = XML.load(s3InputStream(filePath))

    val dataRows = (xmlData \ "person").map {
      row =>
        val name = (row \ "name").text.trim
        val age = (row \ "age").text.trim.toInt
        Schema(name, age)
    }

    val avroRecords: Seq[Record] = dataRows.map {
      row =>
        RecordFormat[Schema].to(row)
    }

    avroRecords
  }
}
