package snoopydoo

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._
import scala.concurrent.duration._
import SnoopySuggestionAction._
import SnoopyHotelListAction._
import SnoopyHotelSelectAction._
import SnoopyHotelDetailAction._
import WoodstockHotelInputAction._
import WoodstockHotelConfirmAction._
import TestUtil._

object SnoopySuggestScenario {

  val scn = scenario("Region Suggestion")
                      .repeat(1000) {
                        feed(SnoopyRegionFeeder.feeder)
                        .exec(session => {
                           session("RegionType").as[String] match {
                             case "airport" => session.set("regionGroup", "airports")
                             case _ => session
                           }
                         })
                        .exec(SnoopySuggestionAction.act)
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
