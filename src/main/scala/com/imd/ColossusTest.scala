package com.imd

import akka.actor.ActorSystem
import colossus.IOSystem
import colossus.core.Server
import colossus.protocols.http.HttpMethod.Get
import colossus.protocols.http.HttpService
import colossus.protocols.http.UrlParsing.{Root, on}
import colossus.service.Callback
import com.mongodb.casbah.Imports._

import scala.util.Random


/**
  * Created by xiongdi on 11/24/16.
  */
object ColossusTest extends App {


  implicit val actorSystem = ActorSystem()

  implicit val system = IOSystem()

  Server.basic("colossus-server", 9000) {
    new HttpService(_) {
      def handle = {
        case request@Get on Root => {
          Callback.successful(request.ok("Hello, world!"))
        }
      }
    }
  }

}
