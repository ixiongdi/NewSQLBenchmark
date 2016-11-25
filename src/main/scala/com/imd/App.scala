package com.imd

import java.sql.DriverManager

import akka.actor.ActorSystem
import colossus._
import colossus.core._
import colossus.protocols.http.HttpMethod._
import colossus.protocols.http.UrlParsing._
import colossus.protocols.http._
import colossus.service._
import com.zaxxer.hikari.HikariDataSource
import com.mongodb.casbah.Imports._


import scala.util.Random


object CP {
  val ds = new HikariDataSource();
  ds.setJdbcUrl("jdbc:mysql://0.0.0.0:4000/bank?useSSL=false");
  ds.setUsername("root");
  ds.setPassword("");
  ds.setMaximumPoolSize(512)

  def test = {
    val db = ds.getConnection()
    db.createStatement().execute(s"INSERT INTO users (id, balance) VALUES (${Random.nextDouble()}, ${Random.nextDouble()})")
    db.close()
    "ok"
  }
}

class HelloService(context: ServerContext) extends HttpService(context) {
  def handle = {
    case request@Get on Root / "test" => {
      Callback.successful(request.ok(CP.test))
    }
    case request@Get on Root / "hello" => {
      Callback.successful(request.ok("Hello World!"))
    }
  }
}

class HelloInitializer(worker: WorkerRef) extends Initializer(worker) {

  def onConnect = context => new HelloService(context)

}


//object Main extends App {
//
//  implicit val actorSystem = ActorSystem()
//  implicit val io = IOSystem()
//
//  Server.start("hello-world", 9000) { worker => new HelloInitializer(worker) }
////  val times = 1000000
////  val start = System.currentTimeMillis()
////  println(start)
////  for (i <- 1 to times) {
////    CP.test
////  }
////  val end = System.currentTimeMillis()
////  println(end)
////
////  println(times.toDouble / (end-start) * 1000)
//
//}

object Main extends App {

  implicit val actorSystem = ActorSystem()

  implicit val system = IOSystem()

  Server.basic("example-server", 9000){ new HttpService(_) {
    def handle = {
      case request @ Get on Root => {
        Callback.successful(request.ok("Hello world!"))
//        CallbackAsync.success(request.ok("Hello world!"))
      }
    }
  }}
}