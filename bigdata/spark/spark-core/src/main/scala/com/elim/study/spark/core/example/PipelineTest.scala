package com.elim.study.spark.core.example

import org.apache.spark.{SparkConf, SparkContext}

/**
 * Spark在处理数据的过程中是取出一条数据后运行完所有的算子后再继续取下一条数据进行运算，而不是
 * 一次取出所有的数据运行完一个算子后再进行下一个算子的运算。
 */
object PipelineTest {

  /**
   * 本示例输出如下：
   * map----value=AAA
   * map second --- value=AAA
   * filter--------------value=AAA
   * map----value=BBB
   * map second --- value=BBB
   * filter--------------value=BBB
   * map----value=CCC
   * map second --- value=CCC
   * filter--------------value=CCC
   * map----value=DDD
   * map second --- value=DDD
   * filter--------------value=DDD
   *
   * @param args
   */
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local").setAppName("Test")
    val context = new SparkContext(conf)
    val rdd = context.parallelize(Array("AAA", "BBB", "CCC", "DDD"))
    rdd.map(value => {
      println(s"map----value=${value}")
      value // 原样输出
    }).map(value => {
      println(s"map second --- value=${value}")
      value // 原样输出
    }).filter(value => {
      println(s"filter--------------value=${value}")
      true // 全部不过滤
    }).collect()
  }

}
