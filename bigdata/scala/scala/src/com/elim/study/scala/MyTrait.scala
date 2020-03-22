package com.elim.study.scala

/**
  * Trait就相当于Java中的Interface
  */
trait MyTrait {

  /**
    * 定义一个接收两个Int参数的方法，该方法返回Int类型的值。该方法没有实现
    * @param a
    * @param b
    * @return
    */
  def func1(a: Int, b: Int): Int

  /**
    * 定义一个有方法体的接口方法
    * @param a
    * @return
    */
  def func2(a: Int): Int = {
    a * a
  }

  def isEqual(other:Any) : Boolean = {
    other.isInstanceOf[MyTrait] && other.asInstanceOf[MyTrait].func2(2) == 4
  }

}

trait Trait2 {

  def func_trait2() = {
    println("Func_Trait2")
  }

}

trait Trait3 {
  def func_trait3() = {
    println("Func_Trait3")
  }
}

/**
  * 实现接口用关键字extends，第2个接口开始使用with
  */
class MyTraitImpl extends MyTrait with Trait2 with Trait3 {

  /**
    * 实现MyTraint中定义的抽象方法
    * @param a
    * @param b
    * @return
    */
  override def func1(a: Int, b: Int): Int = {
    a + b
  }

}

class ParentClass {

}

/**
  * 第一个是继承的Class，后面两个是实现的接口
  */
class MyTraitImpl2 extends ParentClass with Trait2 with Trait3 {

}

object TraitTest {
  def main(args: Array[String]): Unit = {

    var traitObj = new MyTraitImpl
    println(traitObj.func1(10, 20))
    println(traitObj.func2(10))
    traitObj.func_trait2()
    traitObj.func_trait3()

  }
}