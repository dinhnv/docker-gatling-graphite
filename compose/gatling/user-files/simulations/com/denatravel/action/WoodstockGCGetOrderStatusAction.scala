package snoopydoo

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._
import scala.concurrent.duration._
import WoodstockHeaders._
import HttpProtocol._

object WoodstockGCGetOrderStatusAction {

  val requestGetOrderStatusBody = """
    {
        "processInfoKey": "${processInfoKey}"
    }"""

  val apiRQFormat = """{ "header": %s, "body": %s }"""

  val apiRQString = apiRQFormat.format(SnoopyHeaders.apiHeaders, requestGetOrderStatusBody)

  val act = exec(http("hotel_get_order_status_request")
           .post(HttpProtocol.woodstockBaseURL+"/payment/globalcollect/order/status")
           .headers(WoodstockHeaders.httpHeaderWithCSRFWithRqId)
           .body(StringBody(apiRQString))
           .check(status.is(200))
           .check(header("X-CSRF-TOKEN").saveAs("tokenCSRF"))
           )
           .pause(1)

}
