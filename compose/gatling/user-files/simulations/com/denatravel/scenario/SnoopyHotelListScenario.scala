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

object SnoopyHotelListScenario {

  val scn = scenario("HotelList")
                      .repeat(1000) {
                        feed(SnoopyDestinationFeeder.feeder)
                        .exec(session => {
                           session("RegionID").as[String] match {
                             case _ => session
                           }
                         })
                        .exec(SnoopyHotelListAction.act)
                       }

}
