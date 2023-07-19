package com.immo.data.converters

import com.amazonaws.services.s3.{AmazonS3ClientBuilder, AmazonS3URI}
import com.amazonaws.services.s3.model.S3ObjectInputStream
import com.sksamuel.avro4s.Record

trait BatchFileConverter {
  def convert(filePath : String): Seq[Record]

  def s3InputStream(path: String): S3ObjectInputStream = {
    val client = AmazonS3ClientBuilder.defaultClient()
    val s3Uri = new AmazonS3URI(path)
    val s3Object = client.getObject(s3Uri.getBucket, s3Uri.getKey)
    s3Object.getObjectContent
  }
}
