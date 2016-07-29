package snoopydoo

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._
import scala.concurrent.duration._
import WoodstockHeaders._
import HttpProtocol._

object WoodstockHotelConfirmAction {

  val requestConfirmBody = """
    {
        "processInfoKey": "${processInfoKey}",
        "orderId": "",
        "inputRooms": [
          {
            "firstName": "Tien",
            "lastName": "Test",
            "selectBedType": {
                "id": "${bedId}",
                "description": "${bedDescription}"
            },
            "selectSmokeRef": "${smokeReference}"
          }
        ],
        "registrantInfo": {
            "email": "tientn@evolableasia.vn",
            "firstName": "Tien",
            "lastName": "Test",
            "nationality": {
                "phoneCode": "81",
                "countryCodeTwoLetter": "JP",
                "countryCodeThreeLetter": "JPN",
                "countryName": "Japan"
            },
            "phoneNo": "23423424"
        }
    }"""

  val apiRQFormat = """{ "header": %s, "body": %s }"""

  val apiRQString = apiRQFormat.format(SnoopyHeaders.apiHeaders, requestConfirmBody)

  val act = exec(http("hotel_confirm_request")
           .post(HttpProtocol.woodstockBaseURL+"/hotel/reservation/confirm")
           .headers(WoodstockHeaders.httpHeaderWithCSRF)
           .body(StringBody(apiRQString))
           .check(status.is(200))
           .check(header("X-CSRF-TOKEN").saveAs("tokenCSRF"))
           )
           .pause(1)

}
