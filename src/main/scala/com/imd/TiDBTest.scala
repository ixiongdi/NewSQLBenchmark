package com.imd

import java.util.UUID

import akka.actor.ActorSystem
import colossus.IOSystem
import colossus.core.Server
import colossus.protocols.http.HttpMethod.Get
import colossus.protocols.http.HttpService
import colossus.protocols.http.UrlParsing.{Root, on}
import colossus.service.Callback
import com.zaxxer.hikari.HikariDataSource

import scala.util.Random

object TiDB {
  val ds = new HikariDataSource()
  ds.setJdbcUrl("jdbc:mysql://0.0.0.0:4000/bank?useSSL=false")
  ds.setUsername("root")
  ds.setPassword("")
  ds.setMaximumPoolSize(256)

  val cs = for (i <- 1 to 16) yield ds.getConnection()

  def test = {
//    val db = ds.getConnection()
    cs(Math.abs(Random.nextInt() % 16)).createStatement().execute(s"INSERT INTO accounts (id, balance) VALUES ('${UUID.randomUUID().toString}', ${Random.nextInt()})")
//    db.close()
    "Hello, world!"
  }
}

/**
  * Created by xiongdi on 11/24/16.
  */
object TiDBTest extends App {
  implicit val actorSystem = ActorSystem()

  implicit val system = IOSystem()

  Server.basic("tidb-server", 9000) {
    new HttpService(_) {
      def handle = {
        case request@Get on Root => {
          Callback.successful(request.ok(TiDB.test))
        }
      }
    }
  }
}
