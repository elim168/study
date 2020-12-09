package com.elim.study.spark.stream.examples

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * transform可以对DStream内部的RDD进行操作，返回的结果也需要是一个RDD
 **/
object Transform {

  def main(args: Array[String]): Unit = {
    // Create a local StreamingContext with two working thread and batch interval of 1 second.
    // The master requires 2 cores to prevent a starvation scenario.

    val conf = new SparkConf().setMaster("local[2]").setAppName("TcpSocketWordCount")
    val ssc = new StreamingContext(conf, Seconds(5))

    ssc.sparkContext.setLogLevel("WARN")

    // 正常运行每批次任务都会打印很多INFO日志，通过更改日志级别可以屏蔽这部分日志
//    ssc.sparkContext.setLogLevel("WARN")

    // Create a DStream that will connect to hostname:port, like localhost:9999
    val lines = ssc.socketTextStream("localhost", 9999)

    val blackList = ssc.sparkContext.broadcast(List[String]("zhangsan", "lisi"))
    /**
     * transform可以对DStream内部的RDD进行操作，返回的结果也需要是一个RDD。
     */
    val pairs: DStream[(String, Int)] = lines.transform(lineRDD => {
      println("----------这段在Driver端执行-----------")
      lineRDD.filter(line => {
        var flag = false
        val list = blackList.value
        for (elem <- list if !flag) {
          if (line.contains(elem)) {
            flag = true
          }
        }
        !flag
      }).flatMap(_.split(" "))
        .map(word => (word, 1))
    })

    pairs.print()

    ssc.start()             // Start the computation
    ssc.awaitTermination()  // Wait for the computation to terminate

  }

}

