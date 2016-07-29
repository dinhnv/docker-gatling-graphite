package snoopydoo

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._
import scala.concurrent.duration._
import scala.util.Random
import SnoopyHeaders._

object SnoopySuggestionAction {

  val requestBody = """{ "query": "${RegionNameLong}" }"""
  val apiRQFormat = """{ "header": %s, "body": %s }"""
  val apiRQString = apiRQFormat.format(SnoopyHeaders.apiHeaders, requestBody)

  val act  = exec(http("suggest_request_1")
                       .post("/suggestion/region")
                       .headers(SnoopyHeaders.httpHeader2)
                       .body(StringBody(apiRQString))
                       .check(status.is(200))
                     )
                     .pause(1)

}
