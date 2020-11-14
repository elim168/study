package com.elim.study.spark.core.example

import org.apache.spark.{SparkConf, SparkContext}

/**
 * 简写版的Scala版本单词统计
 */
object ScalaWordcount2 {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local").setAppName("scala-wordcount2-elim")

    new SparkContext(sparkConf)
      .textFile("spark-core/files/wordcount.txt")
      .flatMap(_.toLowerCase().split(" "))
      .map((_, 1))
      .reduceByKey(_ + _)
      .foreach(println)

  }

}
