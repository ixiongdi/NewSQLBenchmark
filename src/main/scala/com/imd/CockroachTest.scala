package com.imd

import akka.actor.ActorSystem
import colossus.IOSystem
import colossus.core.Server
import colossus.protocols.http.HttpMethod.Get
import colossus.protocols.http.HttpService
import colossus.protocols.http.UrlParsing.{Root, on}
import colossus.service.Callback
import com.zaxxer.hikari.HikariDataSource

import scala.util.Random

object CockroachDB {
  val ds = new HikariDataSource()
  ds.setJdbcUrl("jdbc:postgresql://127.0.0.1:26257/bank?sslmode=disable")
  ds.setUsername("root")
  ds.setPassword("")

  def test = {
    val db = ds.getConnection()
    db.createStatement().execute(s"INSERT INTO accounts (id, balance) VALUES (${Random.nextInt()}, ${Random.nextInt()})")
    db.close()
    "Hello, world!"
  }
}

/**
  * Created by xiongdi on 11/24/16.
  */
object CockroachTest extends App {
  implicit val actorSystem = ActorSystem()

  implicit val system = IOSystem()

  Server.basic("cockroach-server", 9000) {
    new HttpService(_) {
      def handle = {
        case request@Get on Root => {
          Callback.successful(request.ok(CockroachDB.test))
        }
      }
    }
  }
}
