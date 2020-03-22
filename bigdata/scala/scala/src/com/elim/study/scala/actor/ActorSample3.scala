package com.elim.study.scala.actor

import akka.actor.{Actor, ActorRef, ActorSystem, Props}


object ActorSample3 {

  def main(args: Array[String]): Unit = {
    val system = ActorSystem("test-actor-akka")
    val server = system.actorOf(Props[Server], "server")
    val client = system.actorOf(Props[Client], "client")
    //client给server发送一条消息
    server ! Message(client, "Hello Server, I am Client!")
  }

}

/**
  * 定义一个样例类，里面包装了信息发送方和消息
  *
  * @param actor   信息发送方
  * @param message 消息
  */
case class Message(actor: ActorRef, message: String)

class Server extends Actor {
  var count = 0;

  override def receive: Receive = {
    case message: Message => {
      this.count += 1
      println("Server收到了一条消息---" + message)
      if (this.count <= 10) {
        //给消息的发送方回一条消息
        message.actor ! Message(self, "Server-Response-消息-" + count)
      } else {
        println("Server接收到的消息数量已经超过了预设的%d，将停止服务".format(count - 1))
        context.stop(self)
        context.system.terminate()
      }
    }
  }
}

class Client extends Actor {
  var count = 0;

  override def receive: Receive = {
    case message: Message => {
      this.count += 1
      println("Client收到了一条消息---" + message)
      if (this.count <= 10) {
        message.actor ! Message(self, "Client-Response-消息-" + this.count)
      }
    }
  }
}