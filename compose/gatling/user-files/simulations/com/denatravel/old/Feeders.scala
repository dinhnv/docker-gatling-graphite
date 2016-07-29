package basic

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._
import scala.concurrent.duration._
import scala.util.Random


object Feeders {
	val tokenGenerator = new BearerTokenGenerator
	val prefix = "sim-requestid"

	def isPackageRateFeeder: Feeder[Boolean] = new Feeder[Boolean] {
		override def hasNext = true
		override def next(): Map[String, Boolean] = Map("isPackageRate" -> Random.shuffle(Array(true, false).toList).head)
	}

	def requestIdFeeder: Feeder[String] = new Feeder[String] {
		override def hasNext = true
		override def next(): Map[String, String] = Map("requestId" -> tokenGenerator.generateMD5Token(prefix))
	}

	// circular
  	val regionsFeeder = ssv("ParentRegionList.txt", '#').random
}