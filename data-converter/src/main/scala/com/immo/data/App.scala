package com.immo.data

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import com.immo.data.http.DataMeshRoutes.routes

import scala.concurrent.ExecutionContextExecutor

object App extends scala.App {

  implicit val system: ActorSystem = ActorSystem("data-mesh")
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  Http().newServerAt("localhost", 8080).bind(routes).onComplete {
    case scala.util.Success(binding) =>
      val address = binding.localAddress
      system.log.info("Server online at http://{}:{}/", address.getHostString, address.getPort)
    case scala.util.Failure(ex) =>
      system.log.error("Failed to bind HTTP endpoint, terminating system: {}", ex)
      system.terminate()
  }

  system.registerOnTermination(() => {
    producer.close()
  })

}
