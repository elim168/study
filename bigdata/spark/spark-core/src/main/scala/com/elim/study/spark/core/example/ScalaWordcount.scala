package com.elim.study.spark.core.example

import org.apache.spark.{SparkConf, SparkContext}

/**
 * Scala版本单词统计
 */
object ScalaWordcount {

  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf
    // 本地模式运行
    sparkConf.setMaster("local")
    sparkConf.setAppName("Scala-Wordcount-Elim")

    val sparkContext = new SparkContext(sparkConf)
    val lines = sparkContext.textFile("spark-core/files/wordcount.txt")
    val words = lines.flatMap(line => line.toLowerCase().split(" "))
//    val wordPairs = words.map(word => new Tuple2(word, 1))
    val wordPairs = words.map(Tuple2(_, 1))
//    val result = wordPairs.reduceByKey((v1, v2) => v1 + v2)
    val result = wordPairs.reduceByKey(_+_)

    /**
     * 排序
     * 1. 先把Key和Value掉个顺序
     * 2. 按照Key进行排序
     * 3. 输出时再进行反向输出
     */
    result.map(tuple2 => tuple2.swap).sortByKey().foreach(tuple2 => println("单词[" + tuple2._2 + "]出现了" + tuple2._1 + "次"))

    sparkContext.stop();

  }

}
