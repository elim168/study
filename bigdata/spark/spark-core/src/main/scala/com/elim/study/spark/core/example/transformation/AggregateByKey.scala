package com.elim.study.spark.core.example.transformation

import org.apache.spark.{SparkConf, SparkContext}

/**
  * 测试算子——AggregateByKey
  *
  * aggregateByKey与combineByKey的主要区别在于初始值那块，前者直接指定初始值，后者会基于第一个Key的值初始化。
  */
object AggregateByKey {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local").setAppName("AggregateByKey")
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
      * (a,InitValue-1#InitValue-4#InitValue-7)
      * (b,InitValue-2#InitValue-5|6#InitValue-8)
      * (c,InitValue-3#InitValue-9)
      *
      */
    rdd.aggregateByKey("InitValue-")(
      // 指定相同的分区内相同的Key聚合时当前值如何与之前已经聚合的结果或初始值进行聚合
      (prevResult:String, v:Int) => {
        if (prevResult.equals("InitValue-")) {
          prevResult + v
        } else {
          prevResult + "|" + v
        }
      },
      // 指定不同分区的聚合结果如何聚合
      (p1Result, p2Result) => {
        p1Result + "#" + p2Result
      }
    ).foreach(println)
  }

}
