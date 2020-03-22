package com.elim.study.scala

object StringTest {

  def main(args: Array[String]): Unit = {
    //定义一个字符串
    val s = "Hello Scala!"
    //Java中的String有的方法，Scala中的也都有，因为它们用的就是同一个String类。
    println(s.substring(5))


    /**
      * Scala中的StringBuilder和Java中的StringBuilder类似。Java中的StringBuilder中有的方法，Scala中都有。
      * 另外Scala中扩充了一些+相关的方法。
      */
    val builder = new StringBuilder//调用的是无参构造参数，相当于new StringBuilder()
    builder.append("A")
    builder.+('B')  //append()
    builder.++=("C")  //append()
    builder.++=("D")
    println(builder)  //ABCD


  }

}
