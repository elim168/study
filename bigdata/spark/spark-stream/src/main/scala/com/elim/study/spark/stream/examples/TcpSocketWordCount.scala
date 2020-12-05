package com.elim.study.spark.stream.examples

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * 官方示例。
 * <a href="http://spark.apache.org/docs/latest/streaming-programming-guide.html#a-quick-example">参考文档</a>
 *
 * 运行后可以另外打开一个终端在本机运行<code>nc -lk 9999</code>以开启一个Data Server，然后输入一些文本，可以看到我们的TcpSocketWordCount的实时输出信息。
 *
 * Spark Stream 应用运行之后可以通过访问4040端口查看其WEB UI，里面可以看到Stream的运行信息，可通过它来作为参照调节batch的间隔时间。
 */
object TcpSocketWordCount {

  def main(args: Array[String]): Unit = {
    // Create a local StreamingContext with two working thread and batch interval of 1 second.
    // The master requires 2 cores to prevent a starvation scenario.

    val conf = new SparkConf().setMaster("local[2]").setAppName("TcpSocketWordCount")
    val ssc = new StreamingContext(conf, Seconds(1))

    // 正常运行每批次任务都会打印很多INFO日志，通过更改日志级别可以屏蔽这部分日志
//    ssc.sparkContext.setLogLevel("WARN")

    // Create a DStream that will connect to hostname:port, like localhost:9999
    val lines = ssc.socketTextStream("localhost", 9999)

    // Split each line into words
    val words = lines.flatMap(_.split(" "))

    // Count each word in each batch
    val pairs = words.map(word => (word, 1))
    val wordCounts = pairs.reduceByKey(_ + _)

    // Print the first ten elements of each RDD generated in this DStream to the console
//    wordCounts.print()
    // 也可以指定需要打印的数量，不指定，默认是10
//    wordCounts.print(100)

    wordCounts.foreachRDD(rdd => {
      // 这段代码在Driver端执行
      val broadcastObj = rdd.sparkContext.broadcast("hello")
      val tuples = rdd.filter(w => {
        println(s"================filter value: broadcast==============${broadcastObj.value}")
        true
      }).collect()
      tuples.foreach(println)
    })

    ssc.start()             // Start the computation
    ssc.awaitTermination()  // Wait for the computation to terminate

  }

}

