package snoopydoo

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._
import io.gatling.commons.validation.Success
import io.gatling.http.check.HttpCheck
import scala.concurrent.duration._
import SnoopySuggestionAction._
import SnoopyHotelListAction._
import SnoopyHotelSelectAction._
import SnoopyHotelDetailAction._
import WoodstockHotelInputAction._
import WoodstockHotelConfirmAction._
import TestUtil._

object SnoopyHotelListAirportScenario {

  val scn = scenario("Airport Search")
                      .repeat(185) {
                        feed(SnoopyAirportFeeder.feeder)
                        .exec(TestUtil.showSession)
                        .exec(session => {
                           session("DestinationId").as[String] match {
                             case _ => session
                           }
                         })
                        .exec(SnoopyHotelListForAirportAction.act)
                       }


//  val session: Session = ???
//   val x = session("totalItem").as[String].length()
//                        println("val:" + x.toString)
 
//  val scn = scenario("Scenario HotelList") // A scenario is a chain of requests and pauses
//    .exec(http("request_1")
//      .post("hotel/list")
//      .headers(headers_10)
//      .body(RawFileBody("hotel_list.json")).asJSON)
//    .pause(1) // Note that Gatling has recorded real time pauses

}
