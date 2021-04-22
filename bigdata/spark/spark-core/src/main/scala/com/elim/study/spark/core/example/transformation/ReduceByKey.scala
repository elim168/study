package com.elim.study.spark.core.example.transformation

import org.apache.spark.{SparkConf, SparkContext}

/**
  * 测试算子——ReduceByKey
  */
object ReduceByKey {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local").setAppName("ReduceByKey")
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
      * (a,12)
      * (b,21)
      * (c,12)
      *
      */
    rdd.reduceByKey((v1, v2) => v1 + v2).foreach(println)
  }

}
