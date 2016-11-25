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

object MemSQL {
  val ds = new HikariDataSource()
  ds.setJdbcUrl("jdbc:mysql://192.168.1.127:3306/bank?useSSL=false")
  ds.setUsername("root")
  ds.setPassword("")
  ds.setMaximumPoolSize(128)
  def test = {
    val db = ds.getConnection()
    db.createStatement().execute(s"INSERT INTO accounts (id, balance) VALUES ('${UUID.randomUUID().toString}', ${Random.nextInt()})")
    db.close()
    "Hello, world!"
  }
}

/**
  * Created by xiongdi on 11/24/16.
  */
object MemSQLTest extends App {
  implicit val actorSystem = ActorSystem()

  implicit val system = IOSystem()

  Server.basic("memsql-server", 9001) {
    new HttpService(_) {
      def handle = {
        case request@Get on Root => {
          Callback.successful(request.ok(MemSQL.test))
        }
      }
    }
  }
}
