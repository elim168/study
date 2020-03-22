package com.elim.study

import com.elim.study.scala.maven.Hello

object Test {

  def main(args: Array[String]): Unit = {
    println("Hello Scala")
    val hello = new Hello
    hello.sayHello("World")
  }

  def test() = {
    "Hello Test"
  }

}
