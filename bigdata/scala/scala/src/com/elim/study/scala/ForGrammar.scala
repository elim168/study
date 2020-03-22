package com.elim.study.scala

/**
  * for循环的语法
  */
object ForGrammar {

  def main(args: Array[String]): Unit = {

    var range = 1 to 5
    println(range) // 上面定义的是一个Range对象，从1到5,步长是1
    range = 1 to(10, 3) //从1到10,步长是3
    range = 1.to(5) //等价于上面的1 to 5
    range = 1.to(10, 3) //等价于上面的1 to (10,3)
    var range2 = 1 until(10)  //从1-9,步长是1
    range2 = 1.until(10)  //等价于上面的1 until(10)
    range2 = 1.until(10, 2) //从1-9，步长是2。
    range2.foreach(println)



    //遍历上面定义的range，把每一个值赋值给i。
    for (i <- range) {
      print(i + ", ")
    }

    println()

    // Range后面还可以接if条件进行过滤
    for (i <- range if (i > 5)) {
      print(i + ", ")
    }

    println()
    for (i <- range if (i > 5 && i % 5 == 0)) {
      print(i + ", ")
    }
    println()
    //多个条件判断也可以像下面这样写多个if
    for (i <- range if (i > 5) if (i % 5 == 0)) {
      print(i + ", ")
    }
    println()

    //也可以直接把Range定义在for循环内部
    for (i <- 1 to 100 if (i % 8 == 0)) {
      print(i + "---")
    }
    println()
    range.foreach(println) //foreach中可以接受一个匿名函数，类似Java中的lambda函数。
    range.foreach(s => println(s))  //跟上面是等价的
    //跟上面是等价的，下划线就表示遍历过程中的每一个元素。当匿名函数的参数在方法体中
    // 只用一次时可以用下划线代替，此时连参数名都可以不声明。
    range.foreach(println(_))
    range.foreach(i => println(i))


    /**
      * 两重循环可以简写为下面这样。
      */
    for (i <- 1 to 3; j <- 1 to 3) {
      println(i + "*" + j + "=" + i*j)
    }

  }


}
