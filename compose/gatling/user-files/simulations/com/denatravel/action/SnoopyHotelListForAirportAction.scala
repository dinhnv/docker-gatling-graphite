package snoopydoo

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._
import scala.concurrent.duration._
import SnoopyHeaders._

object SnoopyHotelListForAirportAction {

  val requestBodyForAirport = """
    {
        "destinationId": "${DestinationId}",
        "regionId": ${RegionId},
        "destinationString": "${AirportName}",
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

  val apiRQStringForAirport = apiRQFormat.format(SnoopyHeaders.apiHeaders, requestBodyForAirport)

  val act = exec(http("hotel_list_for_airport")
            .post("/hotel/list")
            .headers(SnoopyHeaders.httpHeader2)
            .body(StringBody(apiRQStringForAirport))
            .check(status.is(200))
            .check(jsonPath("$.body.summaryInfo.totalItemCount").saveAs("totalItem"))
            .check(jsonPath("$.body.hotelInfos[0].hotelSummary.id").saveAs("hotelId"))
            .check(jsonPath("$.body.hotelInfos[0].rateInfos[0].rateId").saveAs("rateId"))
            .check(jsonPath("$.body.hotelInfos[0].rateInfos[0].chargeableRate.total").saveAs("totalAmt"))
            .check(jsonPath("$.body.isPackageRate").saveAs("isPackageRate"))

          )
          .pause(1) // Note that Gatling has recorded real time pauses



}

