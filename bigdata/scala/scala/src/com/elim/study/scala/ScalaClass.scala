package com.elim.study.scala

/**
  * Scala的class是可以带参数的，相当于对应的构造函数，参数必须定义为argName:Type的形式。
  * class带参数时即表示定义了该方法的默认构造函数。
  *
  * 通过<code>new ScalaClass("hello", 123)</code>调用
  *
  * @param xarg1
  * @param xarg2
  */
class ScalaClass(xarg1: String, xarg2: Int) {

  //通过如下方式接受默认的构造方法的参数
  var arg1 = xarg1
  val arg2 = xarg2
  // 默认的访问级别是public，可以加private等修饰符
  private var arg3: String = null
  var arg4 = "Hello 123456"


  /**
    * 定义重载的构造方法，方法名必须是this。方法第一行必须通过this(..)调用默认的构造方法。
    *
    * @param xarg1
    * @param xarg2
    * @param xarg3
    */
  def this(xarg1: String, xarg2: Int, xarg3: String) {
    this(xarg1, xarg2)
    this.arg3 = xarg3
  }

  def sayHello() {
    println("Hello Scala Class")
    val a = 123
    var b = new StringBuffer()
    //    this.arg3
    ScalaClass.helloObject()
    //下面的访问就会报错，因为helloObject()在ScalaClass2中是私有的。上面的ScalaClass.helloObject()
    //可以访问是因为它是定义在当前class的伴生object中的。
    //    ScalaClass2.helloObject()
    val helloStr = this.sayHello("world")
    println(helloStr)
    println(this.sayHello2("world"))
    //也可以通过参数名指定参数
    println(this.sayHello2(name="world", age = 50))
  }

  /**
    * 方法的参数必须指定类型。当应用了=时，方法的最后一行的结果将作为方法的返回值。也可以显示的使用return
    *
    * @param name
    * @return
    */
  def sayHello(name: String) = {
    "Hello " + name
  }

  /**
    * 方法的返回类型也可以显示的指定。比如该方法显示的指定了方法的返回类型是String。
    *
    * @param name
    * @param age 指定了默认值为30,可以在调用时不传递
    * @return
    */
  def sayHello2(name: String, age: Int = 30): String = {
    return "Hello " + name + ", age is " + age
  }

  /**
    * 定义main方法
    *
    * @param args
    */
  def main(args: Array[String]): Unit = {
    //调用带两个参数的构造方法
    val scalaClass = new ScalaClass("hello", 123)
    scalaClass.sayHello()
    println(scalaClass.arg1, scalaClass.arg2, scalaClass.arg3)
    scalaClass.arg1 = "hello again" //给arg1赋值


    //调用带三个参数的构造方法
    val scalaClass2 = new ScalaClass("hello", 123, "world")
    println(scalaClass2.arg1, scalaClass2.arg2, scalaClass2.arg3)
  }

}

/**
  * 在同一个包下面可以有同名的object和class。object叫class的伴生object，class叫object的伴生class，
  * 对应Java它们就是同一个Class。在Scala中同名的Class和object可以相互访问私有的属性和方法。
  */
object ScalaClass {

  private def helloObject(): Unit = {

  }

}

object ScalaClass2 {

  private def helloObject(): Unit = {

  }

}

class ScalaClass3 {

}

/**
  * 继承Class
  */
class ScalaClass4 extends ScalaClass3 {

}
