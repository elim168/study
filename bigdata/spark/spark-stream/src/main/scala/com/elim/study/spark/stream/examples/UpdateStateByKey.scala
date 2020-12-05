package com.elim.study.spark.stream.examples

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.{Durations, StreamingContext}

import scala.concurrent.duration.Duration

/**
 * 之前写的TcpSocketWordCount中统计的单词只是每个批次的，不能统计总的。
 * 使用updateStateByKey方法则可以基于旧值和新的数据计算出新的值。updateStateByKey()属于元组类型的DStream的方法。
 */
object UpdateStateByKey {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("UpdateStateByKey").setMaster("local[2]")
    val ssc = new StreamingContext(conf, Durations.seconds(5))

//    ssc.sparkContext.setLogLevel("WARN")

    /**
     * 需要设置checkpoint目录，updateStateByKey需要把结果写入checkpoint目录下的文件中
     * 默认key的状态在内存中有一份，在checkpoint目录中也有一份。
     *
     * 多久会将内存中的数据写入到磁盘上一份呢？
     *  如果你的批次间隔时间小于10s，那么10s会将内存中的数据写入一份到磁盘，如果批次间隔
     *  时间大于10s，则以批次间隔时间为准。
     *  这样做是为了防止频繁的进行磁盘IO。
     */
    ssc.checkpoint("./checkpoint-dir")

    val lines = ssc.socketTextStream("localhost", 9999)
    val words = lines.flatMap(_.split(" "))
    val pairWords: DStream[(String, Int)] = words.map(word => (word, 1))
    /**
     * updateStateByKey有很多的重载方法。currentValues是一个序列类型，表示的是当前的某个KEY的所有的值，preValue表示以前批次计算出的该KEY的值，
     * 不存在则为空。
     */
    val result: DStream[(String, Int)] = pairWords.updateStateByKey((currentValues: Seq[Int], preValue: Option[Int]) => {
      var total = 0
      if (preValue.isDefined) {
        total += preValue.get
      }
      for (value <- currentValues) {
        total += value
      }
      // 最终需要返回一个Option类型
      Option(total)
    })
    result.print()
    ssc.start()
    ssc.awaitTermination()
    ssc.stop()
  }

}
