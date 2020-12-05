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
object WindowOperator2 {

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
//    val result = pairWords.reduceByKeyAndWindow((v1: Int, v2: Int) => v1 + v2, Durations.seconds(15), Seconds(5))

    /**
     * 第二个参数是inverse reduce function。使用该重载方法，内部计算时会先把上一个时间窗口的结果加上新的时间窗口中多出来的数据，再减去已经过期的数据。
     * 比如原来有s1\s2\s3三个批次的数据，时间窗口是3个批次间隔，窗口间隔是1个批次间隔，则当下一个批次间隔来了数据s4时，先把原来s1\s2\s3计算出来的结果加上新
     * 进来的批次s4的数据，再减去s1中的数据。
     *
     * 这种方式的效率比不带inverse reduce function的更高。它必须指定checkpoint。
     */
    ssc.checkpoint("./checkpoint_dir")
    val result = pairWords.reduceByKeyAndWindow((v1: Int, v2: Int) => v1 + v2, (v1: Int, v2: Int) => v1 - v2, Seconds(15), Seconds(5))


    result.print()

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
