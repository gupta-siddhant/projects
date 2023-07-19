package com.immo.data.http

import akka.http.scaladsl.server.Route
import com.immo.data.kafkaProducer.DataMeshKafkaProducer
import akka.http.scaladsl.server.Directives._

import java.net.URLDecoder
import java.nio.charset.StandardCharsets

object DataMeshRoutes {
  val routes: Route = {
    path("batchReq" / Segment) {
      filePath =>
        get {
          DataMeshKafkaProducer.processAndProduceFile(URLDecoder.decode(filePath, StandardCharsets.UTF_8))
        }
    } ~
      path("singleReq" / Segment) {
        dataRow =>
          get {
            DataMeshKafkaProducer.processAndProducerSingleReq(dataRow)
          }
      }
  }
}
