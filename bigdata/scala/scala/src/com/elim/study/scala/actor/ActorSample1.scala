package com.elim.study.scala.actor

import java.util.concurrent.TimeUnit

import akka.actor.{Actor, ActorSystem, Props}


/**
  * Actor是AKKA的一个通信模型，需要引入AKKA相应的包才行
  */
class MyActor extends Actor {
  /**
    * 重写receive方法进行消息处理
    * 里面应用各种case对不同的消息进行处理，语法即match...case的语法。
    * @return
    */
  override def receive: Receive = {
    case "stop" => {
//      context.stop(self)
//      context.system.terminate()
    }
    case s:Any => {
      println("收到了一条新消息->" + s)
    }
  }

/*  override def preStart(): Unit = {
    super.preStart()
    println("pre start")
  }

  override def postStop(): Unit = {
    super.postStop()
    println("post stop")
  }*/
}

object ActorSample1 {

  def main(args: Array[String]): Unit = {
    val actorSystem = ActorSystem("test")//创建一个ActorSystem，名字叫test
    val actorRef = actorSystem.actorOf(Props[MyActor]);//包装一个ActorRef
//    actorSystem.actorOf(Props[MyActor], "还可以多一个参数命个名字");
    actorRef ! "Hello"  //发布一条消息到actorRef。
    actorRef ! "Hello Scala"
    actorRef ! "Hello Akka"
    actorRef ! "Hello Actor"

    TimeUnit.MILLISECONDS.sleep(100)
    actorSystem.stop(actorRef)
    actorSystem.terminate()
  }

}
