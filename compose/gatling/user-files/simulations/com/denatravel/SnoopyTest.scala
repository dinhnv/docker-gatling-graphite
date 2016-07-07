package basic

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._
import scala.concurrent.duration._

/**
 * dinhnv
 */
class SnoopySimulation extends Simulation {

  val rampUpTimeSecs = 20
  val testTimeSecs   = 360
  val noOfUsers      = 50
  val minWaitMs      = 1000 milliseconds
  val maxWaitMs      = 3000 milliseconds

  val apiScheme = scala.util.Properties.envOrElse("API_SCHEME", "http" )
  val apiHost = scala.util.Properties.envOrElse("API_HOST", "localhost" )
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

  val apiRQFormat = """{ "header": %s, "body": %s }"""

  /**
   * Suggestion api
   */
  object Suggestion {
    val URI          = "/suggestion/region"
    val apiName = requestName + "-suggestion"
    
    val requestBody = """{ "query": "${RegionNameLong}" }"""

    val region = exec(
      http(apiName + "-region")
        .post(URI)
        .headers(http_headers)
        .body(StringBody(apiRQFormat.format(hotelHeaders, requestBody)))
        .check(status.is(200))
    )
  }

  object HotelList {
    val URI          = "/hotel/list"
    val apiName = requestName + "-hotellist"
    val customApiRQFormat = """{ "header": %s, "body": %s, "options": %s }"""
    
    val reqBody = """{
      "isPackageRate": ${isPackageRate},
      "regionId": "${RegionID}",
      "regionGroup": "areas",
      "destinationString": "${RegionNameLong}",
      "arrivalDate": "2016-08-26",
      "departureDate": "2016-08-28",
      "rooms": [
        {
          "adult": 1
        }
      ]
    }"""

    val reqOptions = """{
        "order": 0,
      "filter": {
        "pageNum": 1
      }
    }"""

    val list = exec(
      http(apiName)
        .post(URI)
        .headers(http_headers)
        .body(StringBody(customApiRQFormat.format(hotelHeaders, reqBody, reqOptions)))
        .check(status.is(200))
    )
  }

  // define scenario
  val regionSuggestion = scenario(scenarioBaseName + "-suggestion-region")
    .repeat(10) {
      feed(Feeders.regionsFeeder)
      .exec(session => {
        session("RegionType").as[String] match {
          case "airport" => session.set("regionGroup", "airports")
          case _ => session
        }
      })
      .feed(Feeders.requestIdFeeder)
      .feed(Feeders.isPackageRateFeeder)
      .exec(Suggestion.region)
      .pause(1 seconds, 5 seconds)
      .exec(HotelList.list)
    }


  // execute scenario
  setUp(
    regionSuggestion.inject(rampUsers(noOfUsers) over (rampUpTimeSecs seconds))
  ).protocols(httpProtocol)
}
