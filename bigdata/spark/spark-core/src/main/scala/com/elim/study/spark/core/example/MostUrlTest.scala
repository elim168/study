package com.elim.study.spark.core.example

import org.apache.spark.{SparkConf, SparkContext}

/**
 * 取访问量最大的前五个URL
 */
object MostUrlTest {

  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf
    // 本地模式运行
    sparkConf.setMaster("local")
    sparkConf.setAppName("MostUrlTest")

    val sparkContext = new SparkContext(sparkConf)
    val lines = sparkContext.textFile("spark-core/files/log.txt")
    lines.map(line => {
      new Tuple2(line.split(" ").apply(2), 1)
    }).groupByKey().map(tuple2 => {
      Tuple2(tuple2._2.size, tuple2._1)
    }).sortByKey(false).top(5).foreach(println)
  }

}
