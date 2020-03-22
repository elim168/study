package com.elim.study.scala

/**
  * 测试Scala中的集合
  */
object CollectionTest {


  def main(args: Array[String]): Unit = {
    /**
      * 1.数组Array
      */
    //下面定义了一个数组，数组中包含6个元素，它们是不同的类型
    var array1 = Array(1, 2, 3, "A", "B", "C")
    //如果需要限制元素类型，类型Java中的泛型一样，则可以在Array后加上中括号中定义元素类型。
    //如下定义的Array的元素类型必须是Int
    var array2 = Array[Int](1, 2, 3, 4)
    //还可以通过如下方式构造Array，它的长度是3。
    var array3 = new Array[Int](3)
    array3(0) = 1 //给数组的第一个元素赋值
    array3(1) = 2 //给数组的第二个元素赋值
    array3(2) = 3 //给数组的第三个元素赋值
    array3.foreach(println)
    println(array1(4)) //访问数组中的第5个元素
    array2.update(3, 10) //设置第4个元素的值为10
    println(array2(3))
    for (i <- array1) {
      println("============= " + i)
    }


    /**
      * 2.列表List，它的语法与Array类似
      */
    //创建List，包含9个元素，类型任意
    var list1 = List(1, 2, 3, "A", "B", "C", 1, 2, 3)
    //创建一个没有元素的List，元素类型必须是Int
    var list2 = List[Int]()
    //创建一个包含8个元素的List，类型必须是Int
    var list3 = List[Int](1, 2, 3, 4, 5, 6, 7, 8)
    println(list3.take(5)) //获取前5个元素组成的List。List(1, 2, 3, 4, 5)
    println(list3(5)) //取第6个元素
    println(list3.apply(5))
    println(list3.applyOrElse(50, (i: Int) => 100)) //取第51个元素，如果不存在则返回100
    println(list3.slice(1, 5)) //截取第2-6个元素，不包括第6个元素。List(2, 3, 4, 5)
    println(list3)


    /**
      * 3.Set，它的语法和List类似，只是元素不能重复，也无序
      */
    var set1 = Set[Int](1, 2, 3)
    var set2 = Set(1, 2, 3, "A", "B", 1, 2, 3)
    println(set2.size) //5
    println(set2)
    set1 = set1.+(4, 5, 6) //往Set中添加新的元素，然后返回一个新的Set。
    println(set1)
    set1 = set1.-(5) //删除元素5,然后返回一个新的Set。
    println(set1)


    /**
      * 4.Map
      */
    //下面定义了一个Map中拥有三个元素，Key分别为key1/key2/key3。
    //注意它的Key-Value语法有两种形式。小括号包起来的是一个元组
    var map1 = Map(("key1", 1), ("key2", 2), "key3" -> 3, 'a' -> 97)
    var map2 = Map[Int, Int]((1, 2), (2, 4), (3, 8), (4, 16))
    //+以后产生新的对象，需要重新赋值
    map1 = map1.+(("key4", 4), ("key5", 5))
    println(map1)
    map1.keys.foreach(println)
    map1.foreach(println)


    /**
      * 5.元组Tuple
      * 有从Tuple1到Tuple22共22种Tuple，分别支持放1-22个元素
      */
    //new一个包含一个元素的元组Tuple1
    var tuple1 = new Tuple1(10)
    tuple1 = Tuple1(20)//也可以不指定new，直接定义
    tuple1 = Tuple1[Int](30)//也可以指定元素类型

    var tuple3 = new Tuple3(1, 2, "Hello")
    tuple3 = Tuple3(4, 5, "World")
    tuple3 = Tuple3[Int, Int, String](6, 6, "ABC")
    tuple3 = (10, 20, "BBB")//也可以直接使用小括号包起来

    //Tuple22实例
    var tuple22 = (1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22)

    println(tuple3.productElement(2))//获取元素中的第三个元素
    println(tuple3._3)//获取元素中的第三个元素
    println(tuple3.+("AAA")) //(10,20,BBB)AAA
    val iterator = tuple3.productIterator
    for (ele <- iterator) {
      println("当前元素是：" + ele)
    }
    println(tuple22)

    var tuple2 = (20, 30)
    println(tuple2) //(20,30)
    println(tuple2.swap)  //交换元组的位置，只有二元组有这个方法。(30,20)



  }

}
