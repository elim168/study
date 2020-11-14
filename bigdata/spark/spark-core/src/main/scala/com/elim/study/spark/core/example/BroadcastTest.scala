package com.elim.study.spark.core.example

import org.apache.spark.{SparkConf, SparkContext}

/**
 * 广播变量。
 * 当Task需要使用Driver程序中的变量时，正常情况下需要把该变量一并发送给Executor。当在一个Executor中运行了多个Task时，
 * 每个Task都发送一次该变量，会造成资源的浪费，所以此种情况最好是使用广播变量，这样对应的变量只在Executor中存在一份。
 *
 *
 * 广播变量只能在Driver端发布，在Executor中使用。Executor中不能改变广播变量的值。
 * 不能广播RDD。
 */
object BroadcastTest {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local").setAppName("test")
    val context = new SparkContext(conf)
    val filterValue = List[Int](1, 3, 5, 6)

    // 广播变量，然后返回持有广播变量的一个引用
    val broadcastedValue = context.broadcast[List[Int]](filterValue)


    val list = context.parallelize(List[Int](1, 2, 3, 4, 5, 6, 7, 8, 9), 2)
    list.filter(v => {
      // 在内部通过广播变量的.value获取原来广播的变量的值
      val innerValue = broadcastedValue.value
      innerValue.contains(v)
    }).foreach(println)
  }

}
