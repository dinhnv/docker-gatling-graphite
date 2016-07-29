package snoopydoo

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._
import scala.concurrent.duration._
import scala.util.Random

//dinhnv
object WoodstockRequestIdFeeder {
	val tokenGenerator = new BearerTokenGenerator
	val prefix = "sim-requestid"

	def feeder: Feeder[String] = new Feeder[String] {
		override def hasNext = true
		override def next(): Map[String, String] = Map("xRequestId" -> tokenGenerator.generateMD5Token(prefix))
	}
}
