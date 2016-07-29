package snoopydoo

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._
import scala.concurrent.duration._

object HttpProtocol {

  val apiScheme = scala.util.Properties.envOrElse("API_SCHEME", "http" )
  val snoopyAPIHost = scala.util.Properties.envOrElse("SNOOPY_API_HOST", "snoopy-uat4-lb.lan.skygate-global.com" )
  val woodstockAPIHost = scala.util.Properties.envOrElse("WOODSTOCK_API_HOST", "woodstock-uat4-lb.lan.skygate-global.com" )
  val apiPort= scala.util.Properties.envOrElse("API_PORT", "80" )

  val snoopyBaseURL      = "%s://%s:%s".format(apiScheme, snoopyAPIHost, apiPort)
  val woodstockBaseURL      = "%s://%s:%s".format(apiScheme, woodstockAPIHost, apiPort)

  val proto = http
    .baseURL(snoopyBaseURL)
    .acceptHeader("application/json")
    .contentTypeHeader("application/json")
    .doNotTrackHeader("1")
    .acceptEncodingHeader("gzip, deflate")
    .header("Cache-Control", "max-age=0")
    .userAgentHeader("Mozilla/5.0 (Windows NT 5.1; rv:31.0) Gecko/20100101 Firefox/31.0")


}
