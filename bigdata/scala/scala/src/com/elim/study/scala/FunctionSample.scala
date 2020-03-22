package com.elim.study.scala

import java.sql.Timestamp
import java.util.Date

object FunctionSample {

  /**
    * 函数里面是可以嵌套函数的，下面都是嵌套的函数
    *
    * @param args
    */
  def main(args: Array[String]): Unit = {

    /**
      * 1.变长参数在参数类型后面加一个*
      *
      * @param args
      */
    def variableArgs(args: String*) = {
      args.foreach(arg => println("传递的参数是：" + arg))
    }

    variableArgs("A", "B", "C", "D")


    /**
      * 2.匿名函数。后面的内容是匿名函数的定义，类似Java中的lambda，前面的内容是把函数对象赋值给一个变量。
      */
    val anonymousFunc = (abc: String) => {
      println("Hello anonymous function ------------ " + abc)
    }
    anonymousFunc("AAAAAAAAAAAA")


    /**
      * 3.下面这段是介绍偏函数的。
      * 定义一个打印日志的函数，它包含三个参数，时间/日志级别/日志信息
      *
      * @param timestamp
      * @param level
      * @param message
      */
    def log(timestamp: Date, level: String, message: String) = {
      println(level + "--" + timestamp + "---------" + message)
    }

    //下面定义的就是一个偏函数，它是在log()函数的基础上定义的，它固定了level的值为INFO，其它非固定参数用下划线
    //代替，这样就成了一个新的函数，把它命名为info，这样每次调用info()时对应的level就是INFO。
    val info = log(_: Date, "INFO": String, _: String)
    info(new Date(), "Message-1")
    info(new Date(), "Message-2")
    info(new Date(), "Message-3")


    /**
      * 4.高阶函数——函数的参数是函数
      *
      * @param a
      * @param f 是一个函数的定义，冒号后跟的是函数的结构，下面的示例中的表示函数是有两个Int类型的入参，返回值也是Int。
      * @return
      */
    def highFunc(a: Int, f: (Int, Int) => Int) = {
      100 * f(a, 100 + a)
    }

    def addFunc(a: Int, b: Int) = {
      a + b
    }

    var result = highFunc(1000, addFunc)
    println("The Result is: " + result)
    result = highFunc(100, (a, b) => a * b)
    println("The Result is: " + result)

    /**
      * 4.高阶函数——函数的返回值是一个函数。
      * 下面的函数定义的冒号后面的内容就是返回函数的结构，即入参是两个Int，返回值也是Int
      * @param a
      * @return
      */
    def highFunc2(a: Int) : (Int, Int) => Int = {
      return (b:Int, c:Int) => a * (b + c)
    }
    val func = highFunc2(10)
    println(func(20, 50))//700
    //也可以下面这样调用
    println(highFunc2(10)(20, 50))//700


    /**
      * 5.柯里化函数
      * 它是返回值为函数的高阶函数的简化版
      * @param a
      * @param b
      * @param c
      */
    def func3(a: Int)(b:Int, c:Int) = {
      a * b * c
    }
    print(func3(10)(20, 30))//6000

  }

}
