package com.elim.study.spark.core.example

import org.apache.spark.{SparkConf, SparkContext}

/**
 * 持久化算子有cache、persist和checkpoint三种
 */
object CheckpointTest {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local").setAppName("scala-wordcount2-elim")
    val context = new SparkContext(sparkConf)
    // 指定checkpoint文件的存放路径，它也支持hdfs格式，如hdfs://xxx:9820/aaa。这里指定的本地路径。
    context.setCheckpointDir("./checkpoint")

    val lines = context
      .textFile("spark-core/files/sample.txt")
      .map(_.toLowerCase())

    // 缓存前
    val result = lines.filter(_.startsWith("hello"))

    /**
     * checkpoint会将RDD计算结果存放到文件系统中，下次再执行时就可以直接从文件系统中读取。
     * checkpoint与persist(StorageLevel.DISK_ONLY)的区别是前者在应用结束后文件还会保留，而后者会清除。
     */
    result.checkpoint()
    // 运行了count后会将上面的checkpoint结果保留在文件系统中，可以查看到在./checkpoint目录下多了一个文件。
    result.count()

  }

}
