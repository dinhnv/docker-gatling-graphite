package snoopydoo

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._ 

object TestUtil {

    val setTest = exec(session => {
                        session.set("totalItem", "bar")
                     })
    val showSession = exec(session => { //output session info
                        println(session)
                        session
                      })

}
