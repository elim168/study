package com.elim.study.spark.stream.examples

import java.util.concurrent.TimeUnit

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * 监控文本文件，并将结果写入到文本文件中。也可以监控和写入到HDFS中，写入到HDFS中的是另一个方法。
 * 本示例中的监听文件可由SaveFileUtil工具类产生。
 */
object SaveAndReadTextFile {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setMaster("local").setAppName("TcpSocketWordCount")
    val ssc = new StreamingContext(conf, Seconds(5))

    // 正常运行每批次任务都会打印很多INFO日志，通过更改日志级别可以屏蔽这部分日志
//    ssc.sparkContext.setLogLevel("WARN")

    // 监控一个目录下的文件，新增的文件会被监控到。已经存在的文件不会被监控到
    val files = ssc.textFileStream("./data/streaming-files")

    files.print()

    val result = files
      .flatMap(line => line.split(" "))
      .map(word => (word, 1))
      .reduceByKey(_ + _)

    // prefix和suffix会作为目录的一部分
    result.saveAsTextFiles("./data/saved-files/prefix", "suffix")

    val startTime = System.currentTimeMillis()

    new Thread(() => {
      // 运行三十秒后停止
      TimeUnit.SECONDS.sleep(30)
      ssc.stop(true, true)
    }).start()

    ssc.start()             // Start the computation
    ssc.awaitTermination()  // Wait for the computation to terminate

  }

}

