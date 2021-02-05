package com.elim.study.spark.stream.examples

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * 提交任务时需要指定--supervise
 * 通过设置checkpoint目录和StreamingContext.getOrCreate(..)可以在第一次创建StreamingContext后，把StreamingContext的运行状态等
 * 都存到checkpoint中，之后如果遇到了宕机等情形重新运行程序时可以从checkpoint中恢复StreamingContext及其当前的任务运行进度。
 *
 * 创建StreamingContext中的代码中是包含各种DStream操作的，说白了就是包含了程序的运行逻辑的。下次恢复后会连程序逻辑一起恢复，下次运行的逻辑不能更改算子个数，
 * 可以调整现有算子的运算逻辑。
 */
object StreamingContextHA {

  def main(args: Array[String]): Unit = {
    val checkpointDir = "./checkpoint/streaming-context"
    val ssc = StreamingContext.getOrCreate(checkpointDir, () => {
      createStreamingContext(checkpointDir)
    })

    // 生成文件
    SaveFileUtil.geneFileInNewThread()

    ssc.start()
    ssc.awaitTermination()
    ssc.stop()
  }

  private def createStreamingContext(checkpointDir: String) = {
    println("=====================createStreamingContext=====================")
    val sparkConf = new SparkConf().setAppName("StreamingContextHA").setMaster("local")
    val ssc = new StreamingContext(sparkConf, Seconds(5))
    ssc.sparkContext.setLogLevel("WARN")

    /**
     * 默认checkpoint存储：
     *  1.配置信息
     *  2.DStream操作逻辑
     *  3.job的执行进度
     *  4.offset
     */
    ssc.checkpoint(checkpointDir)

    val files = ssc.textFileStream("./data/streaming-files")

    // 逻辑1
    /*val result = files
      .flatMap(line => line.split(" "))
      .map(word => (word, 1))
      .reduceByKey(_ + _)*/

    /**
     * 逻辑2,当第一次创建了StreamingContext使用的是逻辑1,停止了应用再使用逻辑2运行时由于加了新的算子，运行会报错。如果不新加算子，
     * 只是简单的更改现有算子的逻辑则可以正常运行，也是运行的新的逻辑
      */
    val result = files
      .flatMap(line => line.split(" "))
//      .map(word => {
//        println(s"============map11111111111111111=========${word}")
//        word
//      })
      .map(word => {
        println(s"==================Map-----${word}")
        (word, 1000)
      })
      .reduceByKey(_ + _)
    result.print()
    ssc
  }
}
