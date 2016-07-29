package basic

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class HotelListSimulation extends Simulation {

  val httpConf = http
    .baseURL("http://snoopy-uat4-lb.lan.skygate-global.com/")
    .acceptHeader("application/json,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8") // Here are the common headers
    .doNotTrackHeader("1")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")

  val headers_10 = Map("Content-Type" -> "application/json") // Note the headers specific to a given request

  val scn = scenario("Scenario HotelList") // A scenario is a chain of requests and pauses
    .exec(http("request_1")
      .post("hotel/list")
      .headers(headers_10)
      .body(RawFileBody("hotel_list.json")).asJSON)
    .pause(1) // Note that Gatling has recorded real time pauses

  //setUp(scn.inject(atOnceUsers(1000)).protocols(httpConf))

  val rampUpTimeSecs = 20
  val noOfUsers      = 100

  // execute scenario
  setUp(
    scn.inject(rampUsers(noOfUsers) over (rampUpTimeSecs seconds))
  ).protocols(httpConf)

}
