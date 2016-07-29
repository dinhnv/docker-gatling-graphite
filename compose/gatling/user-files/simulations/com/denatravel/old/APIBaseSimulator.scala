package basic

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._
import scala.concurrent.duration._

/**
 * dinhnv
 */
class APIBaseSimulator extends Simulation {

  val rampUpTimeSecs = 20
  val testTimeSecs   = 360
  val noOfUsers      = 50
  val minWaitMs      = 1000 milliseconds
  val maxWaitMs      = 3000 milliseconds

  val apiScheme = scala.util.Properties.envOrElse("API_SCHEME", "http" )
  val apiHost = scala.util.Properties.envOrElse("API_HOST", "snoopy-uat4-lb.lan.skygate-global.com" )
  val apiPort= scala.util.Properties.envOrElse("API_PORT", "80" )

  val baseURL      = "%s://%s:%s".format(apiScheme, apiHost, apiPort)
  val baseName     = "snoopy"
  val requestName  = baseName + "-request"
  val scenarioBaseName = baseName + "-scenario"

  val httpProtocol = http.baseURL(baseURL)
    .acceptHeader("application/json")
    .contentTypeHeader("application/json")
    .acceptEncodingHeader("gzip, deflate")
    .header("Cache-Control", "max-age=0")
    .userAgentHeader("Mozilla/5.0 (Windows NT 5.1; rv:31.0) Gecko/20100101 Firefox/31.0")

  val tokenGenerator = new BearerTokenGenerator
  val instance = "snoopy"

  val http_headers = Map("X-Request-ID" -> "${requestId}")

  val hotelHeaders = """{
          "uid": "0ABAA87F-079C-3915-3CF2-65D2B6904A89",
          "languageCode": "en",
          "countryCode": "US",
          "currencyCode": "USD",
          "inflow": "FRONT",
          "deviceType": "pc",
          "userAgent": "Mozilla/5.0 (iPhone; CPU iPhone OS 8_0 like Mac OS X) AppleWebKit/600.1.3 (KHTML, like Gecko) Version/8.0 Mobile/12A4345d Safari/600.1.4",
          "userIp": "127.0.0.1"
      }
  """

  val requestBody = """{ "query": "Tokyo, Japan" }"""
  val apiRQFormat = """{ "header": %s, "body": %s }"""

  def ExecRequest() {

    /***
    val scn = scenario("Scenario HotelList") // A scenario is a chain of requests and pauses
      .exec(http("request_1")
        .post("hotel/list")
        .headers(headers_10)
        .body(RawFileBody("hotel_list.json")).asJSON)
      .pause(1) // Note that Gatling has recorded real time pauses

    val rampUpTimeSecs = 20
    val noOfUsers      = 100

    // execute scenario
    setUp(
      scn.inject(rampUsers(noOfUsers) over (rampUpTimeSecs seconds))
    ).protocols(httpConf)
    ***/

    val scn = scenario("Scenario Suggestion")
               .exec(http("request_1")
                     .post("/suggestion/region")
                     .headers(http_headers)
                     .body(StringBody(apiRQFormat.format(hotelHeaders, requestBody)))
                     .check(status.is(200))
                    )

    // execute scenario
    setUp(
      scn.inject(rampUsers(noOfUsers) over (rampUpTimeSecs seconds))
    ).protocols(httpProtocol)

  }


}
