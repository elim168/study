package com.elim.study.spark.core.example

import org.apache.spark.{SparkConf, SparkContext}

/**
 * 数据抽样测试
 */
object SampleTest {


  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf()
    sparkConf.setAppName("Sample Test").setMaster("local")

    val sparkContext = new SparkContext(sparkConf)
    // 第二个参数minPartitions指定了最小的分区数，每个分区是可以分开进行计算的。
    val lines = sparkContext.textFile("./spark-core/files/sample.txt", 5)
    /**
     * 第一个参数withReplacement表示已经抽取出来的样本在下一次抽取时是否重新放回去。true表示会重新放回。
     * 第二个参数fraction表示抽取的样本的比例
     * 第三个参数seed表示抽取数据的种子数，该参数是可选的。当指定了该参数时相同的数据抽取出来的样本每次都是一样的，否则每次可能不一样。
     */
    val result = lines.sample(false, 0.05, 500)
    result.foreach(println)

    // count是调用的count()，用来对RDD进行计数
    println(result.count)

    // 关闭SparkContext
    sparkContext.stop()
  }

}
