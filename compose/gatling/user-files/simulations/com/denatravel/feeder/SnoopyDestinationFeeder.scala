package snoopydoo

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._
import scala.concurrent.duration._


object SnoopyDestinationFeeder {

  	val feeder = ssv("DestinationString.txt", '#').random

}
