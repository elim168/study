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
 * 除了reduceByKeyAndWindow外还有很多的window操作，如countByValueAndWindow()、groupByKeyAndWindow()等，其中最基础的是window()
 */
object WindowOperator3 {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("WindowOperator").setMaster("local[5]")
    val ssc = new StreamingContext(conf, Durations.seconds(5))

//    ssc.sparkContext.setLogLevel("WARN")
//    ssc.checkpoint("./checkpoint_dir")

    val lines = ssc.socketTextStream("localhost", 9999)
    val words = lines.flatMap(_.split(" "))
    val pairWords: DStream[(String, Int)] = words.map(word => (word, 1))

    /**
     * window()函数是最通用的窗口操作，通过它取得一批窗口数据后可以进行任何操作
     */
    val windowData: DStream[(String, Int)] = pairWords.window(Seconds(15), Seconds(5))
    windowData.reduceByKey(_+_).print()

    ssc.start()
    ssc.awaitTermination()
    ssc.stop()

    /**
     *
     * 在第一个批次间隔内输入“hello java”，在第二个批次间隔内输入“hello scala”，在第三个批次间隔内输入“hello spark”，会看到下面这样的输出。
     * 可以看到曾经出现过的数据，最后被滑走了后对应的Key还存在，只是它的值变为了0。
     * -------------------------------------------
     * Time: 1607180645000 ms
     * -------------------------------------------
     * (hello,1)
     * (java,1)
     *
     * -------------------------------------------
     * Time: 1607180650000 ms
     * -------------------------------------------
     * (scala,1)
     * (hello,2)
     * (java,1)
     *
     * -------------------------------------------
     * Time: 1607180655000 ms
     * -------------------------------------------
     * (scala,1)
     * (hello,3)
     * (java,1)
     * (spark,1)
     *
     * -------------------------------------------
     * Time: 1607180660000 ms
     * -------------------------------------------
     * (scala,1)
     * (hello,2)
     * (java,0)
     * (spark,1)
     *
     * -------------------------------------------
     * Time: 1607180665000 ms
     * -------------------------------------------
     * (scala,0)
     * (hello,1)
     * (java,0)
     * (spark,1)
     *
     * -------------------------------------------
     * Time: 1607180670000 ms
     * -------------------------------------------
     * (scala,0)
     * (hello,0)
     * (java,0)
     * (spark,0)
     *
     * -------------------------------------------
     * Time: 1607180675000 ms
     * -------------------------------------------
     * (scala,0)
     * (hello,0)
     * (java,0)
     * (spark,0)
     */

  }

}
