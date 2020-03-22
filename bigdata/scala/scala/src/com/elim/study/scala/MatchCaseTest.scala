package com.elim.study.scala

/**
  * Scala中的match...case...相当于Java中的switch...case...
  */
object MatchCaseTest {

  def main(args: Array[String]): Unit = {

    val abc = List(1, "AB", 'D', true, 2.0)
    abc.foreach(i => {
      //拿i进行匹配，相当于Java中的switch(i)
      i match {
        //当i的值为1的时候执行后面的输出语句，每一个匹配项匹配后会自动break，
        //即最多只会有一个匹配项能够命中
        case 1 => println("Value is 1")
        case true => println("Value is 2")
        //match...case...还可以按类型进行匹配。
        case s:String => println("按类型进行匹配，匹配String类型----" + s)
        case _ => println("下划线相当于默认值，相当于switch case中的default")
      }
    })

  }

}
