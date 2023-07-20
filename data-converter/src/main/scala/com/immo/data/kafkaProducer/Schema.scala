package com.immo.data.kafkaProducer

case class Schema(
  residentId : Int,
  residentName : String,
  preferredCountry : String,
  preferredState : String,
  preferredCity : String,
  budget : Double
)
