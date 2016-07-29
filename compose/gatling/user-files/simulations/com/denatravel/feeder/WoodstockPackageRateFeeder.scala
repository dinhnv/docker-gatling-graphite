package snoopydoo

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._
import scala.concurrent.duration._
import scala.util.Random

//dinhnv
object WoodstockPackageRateFeeder {

	def feeder: Feeder[Boolean] = new Feeder[Boolean] {
		override def hasNext = true
		override def next(): Map[String, Boolean] = Map("isPackageRate" -> Random.shuffle(Array(true, false).toList).head)
	}

}
