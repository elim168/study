package com.elim.study.spark.stream.examples

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.{Durations, Seconds, StreamingContext}

/**
 * 窗口操作，它可以以滑动窗口的方式取过去多少时间的数据进行操作。它有两个核心概念，窗口间隔和窗口长度。
 *
 *    窗口间隔：每隔多少时间取一次数据；
 *    窗口长度：每次取的是过去多少时间的数据。
 *
 * 窗口间隔和窗口长度都必须是批次间隔的整数倍。
 *
 */
object WindowOperator {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("WindowOperator").setMaster("local[2]")
    val ssc = new StreamingContext(conf, Durations.seconds(5))

//    ssc.sparkContext.setLogLevel("WARN")

    val lines = ssc.socketTextStream("localhost", 9999)
    val words = lines.flatMap(_.split(" "))
    val pairWords: DStream[(String, Int)] = words.map(word => (word, 1))
    /**
     * reduceByKeyAndWindow有很多的重载方法。
     * 下面的第一个参数表示遇到了相同的Key时如何对Value进行计算；第二个参数表示窗口长度；第三个参数表示窗口间隔。
     */
    val result = pairWords.reduceByKeyAndWindow((v1: Int, v2: Int) => v1 + v2, Durations.seconds(15), Seconds(5))

    result.print()

    ssc.start()
    ssc.awaitTermination()
    ssc.stop()

    /**
     *
     * 在第一个批次间隔内输入“hello java”，在第二个批次间隔内输入“hello scala”，在第三个批次间隔内输入“hello spark”，会看到下面这样的输出。
     * 可以看到Java一共存在于3个批次间隔内，到第4个间隔时已经被滑走了。
     * -------------------------------------------
     * Time: 1607142695000 ms
     * -------------------------------------------
     * (Java,1)
     * (hello,1)
     *
     * -------------------------------------------
     * Time: 1607142700000 ms
     * -------------------------------------------
     * (scala,1)
     * (Java,1)
     * (hello,2)
     *
     * -------------------------------------------
     * Time: 1607142705000 ms
     * -------------------------------------------
     * (scala,1)
     * (Java,1)
     * (hello,3)
     * (spark,1)
     *
     * -------------------------------------------
     * Time: 1607142710000 ms
     * -------------------------------------------
     * (scala,1)
     * (hello,2)
     * (spark,1)
     *
     * -------------------------------------------
     * Time: 1607142715000 ms
     * -------------------------------------------
     * (hello,1)
     * (spark,1)
     *
     * -------------------------------------------
     * Time: 1607142720000 ms
     * -------------------------------------------
     */

  }

}
