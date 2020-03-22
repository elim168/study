package com.elim.study.scala;

/**
 * Java Class可以调用Scala中定义的Class和Object
 */
public class JavaClass {

  public static void main(String[] args) {
    //调用object中的方法
    MyObject.sayHello();
    //访问object中定义的静态变量，编译为class后变为了对应的方法。
    System.out.println(MyObject.static_field1());

    //访问Scala Class
    ScalaClass scalaClass = new ScalaClass("hello", 123);
    System.out.println(scalaClass.arg1() + scalaClass.arg2());
    scalaClass.sayHello();
    System.out.println(scalaClass.arg4());
    // 设置了属性arg4的值为ABCDEFG，相当于Java中的Setter方法。
    scalaClass.arg4_$eq("ABCDEFG");
    System.out.println(scalaClass.arg4());
//    scalaClass.main(null);


  }

}
