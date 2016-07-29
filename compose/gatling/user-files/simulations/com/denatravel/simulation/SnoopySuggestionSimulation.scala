package snoopydoo

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._
import scala.concurrent.duration._
import HttpProtocol._

class SnoopySuggestionSimulation extends Simulation {

  val rampUpTimeSecs = 20
  val testTimeSecs   = 360
  val noOfUsersHotelList  = 10
  val noOfUsersSuggestion = 2
  val minWaitMs      = 1000 milliseconds
  val maxWaitMs      = 3000 milliseconds

  // execute scenario
  setUp(
    SnoopySuggestScenario.scn.inject(rampUsers(noOfUsersSuggestion) over (rampUpTimeSecs seconds))
  ).protocols(HttpProtocol.proto)

  after {
    println("after test")
  }

}
