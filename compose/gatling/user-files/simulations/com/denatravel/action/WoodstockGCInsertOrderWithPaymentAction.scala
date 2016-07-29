package snoopydoo

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._
import scala.concurrent.duration._
import WoodstockHeaders._
import HttpProtocol._

object WoodstockGCInsertOrderWithPaymentAction {

  val requestGCInsertBody = """
    {
        "processInfoKey": "${processInfoKey}",
        "selectCardInfo": {
            "cancelCallbackUrl": "https://local.au.skygate-global.com/hotel/reservation/cushion", 
            "cardType": "visa", 
            "nextCallbackUrl": "https://local.au.skygate-global.com/hotel/reservation/cushion"
        },
        "fraudInfo": {
            "contactf": "1",
            "firstname": "T*e*n",
            "lastname": "T*a*n",
            "email": "t*e*t*n@evolableasia.vn",
            "phonecode": "61",
            "phonecodetwoletter": "AU",
            "phonenumber": "9*8*4*1*3",
            "state": "New South",
            "country": "Australia", 
            "countrythreeletter": "AUS", 
            "countrytwoletter": "AU", 
            "customeripaddress": "127.0.0.1"
        }
    }"""

  val apiRQFormat = """{ "header": %s, "body": %s }"""

  val apiRQString = apiRQFormat.format(SnoopyHeaders.apiHeaders, requestGCInsertBody)

  val act = exec(http("hotel_gc_insert_order_with_payment_request")
           .post(HttpProtocol.woodstockBaseURL+"/payment/globalcollect/order/insert")
           .headers(WoodstockHeaders.httpHeaderWithCSRFWithRqId)
           .body(StringBody(apiRQString))
           .check(status.is(200))
           .check(header("X-CSRF-TOKEN").saveAs("tokenCSRF"))
           )
           .pause(1)

}
