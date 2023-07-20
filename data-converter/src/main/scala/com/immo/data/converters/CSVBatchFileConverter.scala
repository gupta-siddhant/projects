package com.immo.data.converters

import com.immo.data.kafkaProducer.Schema
import com.sksamuel.avro4s.{Record, RecordFormat}
import org.apache.commons.csv.{CSVFormat, CSVParser}

import java.nio.charset.StandardCharsets
import scala.collection.JavaConverters._

class CSVBatchFileConverter extends BatchFileConverter {
  override def convert(filePath: String): Seq[Record] = {
    val parser: CSVParser = CSVParser.parse(s3InputStream(filePath), StandardCharsets.UTF_8, CSVFormat.DEFAULT)

    val dataRows = parser.getRecords.asScala.toList.map {
      record =>
        Schema(
          record.get(0).toInt,
          record.get(1),
          record.get(2),
          record.get(3),
          record.get(4),
          record.get(5).toDouble
        )
    }
    parser.close()

    val avroRecords = dataRows.map {
      row =>
        RecordFormat[Schema].to(row)
    }

    avroRecords
  }
}
