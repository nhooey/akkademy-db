package com.akkademy

import akka.actor.{Actor, Status}
import akka.event.Logging
import com.akkademy.messages.{DelRequest, GetRequest, SetRequest, KeyNotFoundException}

import scala.collection.mutable

class AkkademyDb extends Actor {
  val map = new mutable.HashMap[String, Object]
  val log = Logging(context.system, this)
  var lastMessage : SetRequest = SetRequest("_", "_")

  override def receive: PartialFunction[Any, Unit] = {

    case SetRequest(key: String, value: Object) =>
      log.info("received SetRequest - key: '{}', value: '{}'", key, value)
      map.put(key, value)
      lastMessage = SetRequest(key, value)
      sender() ! Status.Success

    case GetRequest(key: String) =>
      log.info("received GetRequest - key: '{}'", key)
      val response : Option[Object] = map.get(key)

      response match {
        case Some(x: Any) =>
          sender() ! x
        case None =>
          sender() ! Status.Failure(new KeyNotFoundException(key))
      }

    case DelRequest(key: String) =>
      log.info("received DelRequest - key: '{}'", key)
      map.remove(key)
      sender() ! Status.Success

    case o =>
      Status.Failure(new ClassNotFoundException)
  }
}
