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
import WoodstockHotelReservationAction._
import WoodstockHotelReservationCompleteAction._
import WoodstockGetPaymentInfoAction._
import TestUtil._

object MixedScenario {

  val scn = scenario("HotelListForSelect")
                     .exec(SnoopyHotelListAction.act)
                     .exec(session => {
                           session("isPackageRate").as[String] match {
                             case "true" => session.set("isPackageRateFlg", "true")
                             case _ => session.set("isPackageRateFlg", "false")
                           }
                         })
                     .exec(TestUtil.showSession)
                     .exec(TestUtil.setTest)
                     .pause(1)
                     .exec(SnoopyHotelDetailAction.act)
                     .exec(session => {
                           session("isPackageRate").as[String] match {
                             case "true" => session.set("isPackageRateFlg", "true")
                             case _ => session.set("isPackageRateFlg", "false")
                           }
                         })
                     .exec(TestUtil.showSession)
                     .pause(1)
                     .exec(SnoopyHotelSelectAction.act)
                      .exec(session => {
                           session("isPackageRate").as[String] match {
                             case "true" => session.set("isPackageRateFlg", "true")
                             case _ => session.set("isPackageRateFlg", "false")
                           }
                         })
                     .exec(TestUtil.showSession)
                     .pause(1)
                      .exec(session => {
                           session.set("xUserId", "1000")
                         })
                     .exec(WoodstockHotelInputAction.act)
                     .exec(TestUtil.showSession)
                     .pause(1)
                     .exec(WoodstockHotelConfirmAction.act)
                     .exec(TestUtil.showSession)
                     .pause(1)
                     .feed(WoodstockRequestIdFeeder.feeder)
                     .exec(WoodstockGetPaymentInfoAction.act)
                     .exec(TestUtil.showSession)
                     .pause(1)
                     .feed(WoodstockRequestIdFeeder.feeder)
                     .exec(WoodstockGCInsertOrderWithPaymentAction.act)
                     .exec(TestUtil.showSession)
                     .pause(1)
                     .feed(WoodstockRequestIdFeeder.feeder)
                     .exec(WoodstockGCGetOrderStatusAction.act)
                     .exec(TestUtil.showSession)
                     .pause(1)                   
                     .feed(WoodstockRequestIdFeeder.feeder)
                     .exec(WoodstockHotelReservationAction.act)
                     .exec(TestUtil.showSession)
                     .pause(1)
                     .exec(WoodstockHotelReservationCompleteAction.act)
                     .pause(1)

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
