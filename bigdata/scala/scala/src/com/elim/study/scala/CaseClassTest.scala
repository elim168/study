package com.elim.study.scala

/**
  * 采用case修饰的class会自动为构造方法参数生成getter和setter，因为构造参数默认是val的，
  * 所以默认不生成setter，如果需要生成setter，需要把构造参数修饰为var，如下面的age参数。
  * case修饰的class通常不需要有class体，当然也可以写，可以在其中定义一些方法之类的。
  *
  * case修饰的class会自动基于构造方法的参数生成equals/hashcode等方法，所以只要构造参数的值是一样的，
  * 它们的equals结果一定是true。
  * @param name
  * @param age
  */
case class Person(name: String, var age: Int)

class Person2(name: String, var age: Int)

object CaseClassTest {

  def main(args: Array[String]): Unit = {
    val p1 = Person("zhangsan", 30)
    val p2 = Person("zhangsan", 30)
    assert(p1.equals(p2))

    val p3 = new Person2("zhangsan", 30)
    val p4 = new Person2("zhangsan", 30)
    assert(!p3.equals(p4))
  }

}
