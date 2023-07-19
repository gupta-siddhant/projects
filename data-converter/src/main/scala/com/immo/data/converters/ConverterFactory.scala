package com.immo.data.converters

object ConverterFactory {

  def getConverter(filePath : String): BatchFileConverter = {
    val index = filePath.lastIndexOf('.')
    val extension = filePath.substring(index+1)
    extension match {
      case "xml" =>
        new XMLBatchFileConverter
      case "json" =>
        new JSONBatchFileConverter
      case "csv" =>
        new CSVBatchFileConverter
      case _ =>
        null
    }
  }

}
