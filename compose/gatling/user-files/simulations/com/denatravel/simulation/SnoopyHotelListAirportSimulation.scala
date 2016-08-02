package snoopydoo

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._
import scala.concurrent.duration._
import HttpProtocol._

class SnoopyHotelListAirportSimulation extends Simulation {

  val rampUpTimeSecs = 100
  val testTimeSecs   = 360
  val noOfUsersHotelList  = 1
  val minWaitMs      = 1000 milliseconds
  val maxWaitMs      = 3000 milliseconds

  // execute scenario
  setUp(
    SnoopyHotelListAirportScenario.scn.inject(rampUsers(noOfUsersHotelList) over (rampUpTimeSecs seconds))
  )
  .protocols(HttpProtocol.proto)

  after {
    println("after test")
  }

}
