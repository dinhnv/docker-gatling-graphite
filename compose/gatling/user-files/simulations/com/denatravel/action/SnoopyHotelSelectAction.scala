package snoopydoo

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._
import scala.concurrent.duration._
import SnoopyHeaders._
import HttpProtocol._

object SnoopyHotelSelectAction {

  val requestProductSelectBody = """
    {
        "isPackageRate": ${isPackageRateFlg},
        "productCacheKey": "${productCacheKey}",
        "rateId": "${rateId}"
    }"""

  val apiRQFormat = """{ "header": %s, "body": %s }"""
  val apiRQString = apiRQFormat.format(SnoopyHeaders.apiHeaders, requestProductSelectBody)

  val act = exec(http("hotel_list_select_request")
           .post("/hotel/select")
           .headers(SnoopyHeaders.httpHeader2)
           .body(StringBody(apiRQString))
           .check(status.is(200))
           .check(jsonPath("$.body.processInfoKey").saveAs("processInfoKey"))
           .check(jsonPath("$.body.isPackageRate").saveAs("isPackageRate"))
           )
           .pause(1)

}

