package snoopydoo

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._
import scala.concurrent.duration._
import SnoopyHeaders._

object SnoopyHotelListAction {

  val requestBody = """
    {
        "searchId": "",
        "regionId": ${RegionID},
        "destinationString": "${DestinationString}",
        "arrivalDate": "2016-10-18",
        "departureDate": "2016-10-19",
        "rooms": [
            {
                "adult": 1
            }
        ]
    },
    "options": {
        "order": 0,
        "filter": {
            "pageSize": 2
        }
    }"""

  val apiRQFormat = """{ "header": %s, "body": %s }"""
  val apiRQString = apiRQFormat.format(SnoopyHeaders.apiHeaders, requestBody)

  val act = exec(http("hotel_list_request_1")
          .post("/hotel/list")
          .headers(SnoopyHeaders.httpHeader2)
          .body(StringBody(apiRQString))
          .check(status.is(200))
          )
          .pause(1) // Note that Gatling has recorded real time pauses


}

