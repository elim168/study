package com.elim.study.spark.core.example

import org.apache.spark.{SparkConf, SparkContext}

/**
 * 累加器
 * Driver程序中定义的count在Executor中累积后在Driver程序中获取不到累加后的值，对应的值能在Executor中获取。
 */
object AccumulatorTest {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local").setAppName("Test")
    val context = new SparkContext(conf)
    val rdd1 = context.parallelize(0 until (10))
    var count = 0
//    context.doubleAccumulator()
    // 创建一个LongAccumulator，并命名为accumulator1
    val accumulatorCount1 = context.longAccumulator("accumulator1")
    val accumulatorCount2 = context.longAccumulator
    rdd1.map(i => {
      count += 1
      accumulatorCount1.add(1)
      accumulatorCount2.add(2)
      println(s"Executor Count = ${count}")
      println(s"Executor accumulatorCount1=${accumulatorCount1.value}")
      println(s"Executor accumulatorCount2=${accumulatorCount2.value}")
    }).collect()
    // 输出的还是0
    println(s"count=${count}")
    // 输出10
    println(s"accumulatorCount1=${accumulatorCount1.value}")
    println(s"accumulatorCount1=${accumulatorCount1}")

    println(s"accumulatorCount2=${accumulatorCount2}")
    // 输出，可以看到一个是有名字的，一个是没有名字的。
    //accumulatorCount1=LongAccumulator(id: 0, name: Some(accumulator1), value: 10)
    //accumulatorCount2=LongAccumulator(id: 1, name: None, value: 20)
  }

}
