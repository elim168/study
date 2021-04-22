package com.elim.study.spark.core.example.transformation

import org.apache.spark.{SparkConf, SparkContext}

/**
  * 测试算子——CombineByKey
  */
object CombineByKey {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local").setAppName("CombineByKey")
    val sc = new SparkContext(conf)
    // 一共9条数据，分成三个分区
    val rdd = sc.makeRDD(List[(String, Int)](("a", 1), ("b", 2), ("c", 3),
      ("a", 4), ("b", 5), ("b", 6),
      ("a", 7), ("b", 8), ("c", 9)), 3)

    /**
      * 分成三个分区，第一个分区的值为：
      * ("a", 1), ("b", 2), ("c", 3)
      * 第二个分区的值为：
      * ("a", 4), ("b", 5), ("b", 6)
      * 第三个分区的值为：
      * ("a", 7), ("b", 8), ("c", 9)
      *
      */
    rdd.mapPartitionsWithIndex((partition, iterable) => {
      println(s"分区${partition}的值为：")
      iterable.foreach(println)
      iterable
    }, true).count()


    /**
      * 最终输出结果为：
      * (a,Init-1#Init-4#Init-7)
      * (b,Init-2#Init-5|6#Init-8)
      * (c,Init-3#Init-9)
      *
      */
    rdd.combineByKey(
      // 指定同一个分区每个Key的第一个值创建的该Key的初始值
      v => {"Init-" + v},
      // 指定同一个分区中相同Key的值如何聚合，其中initV是由第一个值初始化的初始值或已经聚合其它值的值
      (initV:String, v) => {initV + "|" + v},
      // 指定不同分区间的数据如何聚合
      (v1:String, v2:String) => v1 + "#" + v2)
      .foreach(println)
  }

}
