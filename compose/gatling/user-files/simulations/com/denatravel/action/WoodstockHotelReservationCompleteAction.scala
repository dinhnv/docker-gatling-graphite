package snoopydoo

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._
import scala.concurrent.duration._
import WoodstockHeaders._
import HttpProtocol._

object WoodstockHotelReservationCompleteAction {

  val requestReservationCompleteBody = """
    {
        "processInfoKey": "${processInfoKey}",
    }"""

  val apiRQFormat = """{ "header": %s, "body": %s }"""

  val apiRQString = apiRQFormat.format(SnoopyHeaders.apiHeaders, requestReservationCompleteBody)

  val act = exec(http("hotel_confirm_request")
           .post(HttpProtocol.woodstockBaseURL+"/hotel/reservation/complete")
           .headers(WoodstockHeaders.httpHeaderWithCSRF)
           .body(StringBody(apiRQString))
           .check(status.is(200))
           .check(header("X-CSRF-TOKEN").saveAs("tokenCSRF"))
           )
           .pause(1)

}
