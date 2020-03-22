package com.elim.study.scala

/**
  * scala中的object也是一个class，相当于Java中的单例模式，其中定义的方法和变量都是静态的。
  * 变量一旦被赋值后它就有了类型，之后再给该变量赋新值时一定要能匹配类型，否则报错。
  */
object MyObject {

  /**
    * object里面的属性都是静态属性
    */
  var static_field1 = "hello"
  /**
    * 静态的常量
    */
  val static_field2 = "world"

  /**
    * 这是scala的main方法的写法，相当于java中的main方法，是程序的入口
    * @param args
    */
  def main(args: Array[String]): Unit = {
    // 定义变量，行末可以不加分号
    var a = 1
    // val定义的常量，不可以重新赋值，相当于java中的final
    val b = 1000
    print(a)  //打印a，不换行
    println() //打印，换行
    a = 10
    println(a + b)
    println(static_field1, MyObject.static_field2)  //(hello,world)
    MyObject.static_field1 = "hello1"
    //下面的语句会报错，因为static_field2是val修饰的，是不能被赋值的。
//    MyObject.static_field2 = "world1"

  }

  /**
    * 该方法就是一个静态方法
    */
  def sayHello() = {
    println("Hello World!")
  }

}
