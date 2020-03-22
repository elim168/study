package com.elim.study.scala.actor

import java.util.concurrent.TimeUnit

import akka.actor.{Actor, ActorSystem, Props}
import akka.dispatch.ExecutionContexts
import akka.util.Timeout

/**
  * 测试Actor给Actor发消息，这是方式1,方式2请参考ActorSample3
  */
class ServerActor extends Actor {
  var count = 0
  override def receive: Receive = {
    case message: String => {
      println("ServerActor收到了消息-> " + message)
      count += 1
      if (count <= 10) {
        //获取ClientActor，并往ClientActor发送消息。ClientActor也可以直接传递进来。
        context.system.actorSelection("user/client").resolveOne()(Timeout(1000, TimeUnit.MILLISECONDS)).onComplete(actorRef => {
          actorRef.get ! "Server Response - " + count
        })(ExecutionContexts.global())
      } else {
        context.stop(self)//self是当前Actor
        context.system.terminate()
      }
    }
  }
}

class ClientActor extends Actor {
  var count = 0
  override def receive: Receive = {
    case message: String => {
      println("ClientActor收到了消息-> " + message)
      count += 1
      if (count <= 10) {
        context.system.actorSelection("user/server").resolveOne()(Timeout(1000, TimeUnit.MILLISECONDS)).onComplete(actorRef => {
          actorRef.get ! "Client Response - " + count
        })(ExecutionContexts.global())
      }
    }
  }
}

object ActorSample2 {

  def main(args: Array[String]): Unit = {
    val actorSystem = ActorSystem("test")//创建一个ActorSystem，名字叫test
    val serverActorRef = actorSystem.actorOf(Props[ServerActor], "server");//包装一个ActorRef
    println(serverActorRef.path)  //akka://test/user/server
    actorSystem.actorOf(Props[ClientActor], "client");//包装一个ActorRef
    serverActorRef ! "Hello"  //先往Server发送一条消息
  }

}
