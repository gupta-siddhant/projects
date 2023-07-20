package com.immo.data.converters


import com.immo.data.kafkaProducer.Schema
import com.sksamuel.avro4s.{Record, RecordFormat}

import scala.xml.XML

class XMLBatchFileConverter extends BatchFileConverter {

  override def convert(filePath: String): Seq[Record] = {
    val xmlData = XML.load(s3InputStream(filePath))

    val dataRows = (xmlData \ "resident").map {
      row =>
        val id = (row \ "residentId").text.trim.toInt
        val name = (row \ "residentName").text.trim
        val country = (row \ "preferredCountry").text.trim
        val state = (row \ "preferredState").text.trim
        val city = (row \ "preferredCity").text.trim
        val budget = (row \ "budget").text.trim.toDouble
        Schema(id, name, country, state, city, budget)
    }

    val avroRecords: Seq[Record] = dataRows.map {
      row =>
        RecordFormat[Schema].to(row)
    }

    avroRecords
  }
}
