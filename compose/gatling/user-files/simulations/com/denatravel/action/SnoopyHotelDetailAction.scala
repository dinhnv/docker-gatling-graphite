package snoopydoo

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._
import scala.concurrent.duration._
import SnoopyHeaders._
import HttpProtocol._

object SnoopyHotelDetailAction {

  val requestHotelDetailBody =  """
    {
        "isPackageRate": ${isPackageRateFlg},
        "base": {
            "hotelId": "${hotelId}",
            "arrivalDate": "2016-10-18",
            "departureDate": "2016-10-19"
        },
        "rooms": [
            {
            "adult": 1
            }
        ]
    }"""

  val apiRQFormat = """{ "header": %s, "body": %s }"""
  val apiRQStringForSelect = apiRQFormat.format(SnoopyHeaders.apiHeaders, requestHotelDetailBody)

  val act = exec(http("hotel_detail_for_select")
            .post("/hotel/detail")
            .headers(SnoopyHeaders.httpHeader2)
            .body(StringBody(apiRQStringForSelect))
            .check(status.is(200))
            .check(jsonPath("$.body.productCacheKey").saveAs("productCacheKey"))
            .check(jsonPath("$.body.roomTypes[0].rateInfos[0].rateId").saveAs("rateId"))
            .check(jsonPath("$.body.roomTypes[0].rateInfos[0].bedTypes[0].id").saveAs("bedId"))
            .check(jsonPath("$.body.roomTypes[0].rateInfos[0].bedTypes[0].description").saveAs("bedDescription"))
            .check(jsonPath("$.body.roomTypes[0].rateInfos[0].smokeReference").saveAs("smokeReference"))
            .check(jsonPath("$.body.isPackageRate").saveAs("isPackageRate"))
           )
          .pause(1) // Note that Gatling has recorded real time pauses

}
