package com.elim.study.spark.sql.test

import org.apache.spark.sql.{SaveMode, SparkSession}
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 测试从parquet文件中读取数据。parquet文件是带有压缩功能的。
 */
object WriteAndReadFromParquet {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local").setAppName("test")

    val sparkSession = SparkSession.builder().config(conf).getOrCreate()
    // dataFrame类似一张表，底层是Row类型的RDD
    val dataFrame = sparkSession.read.json("spark-sql/files/persons.json")

    // 写入到一个parquet文件
    dataFrame.write.parquet("spark-sql/files/write/persons.parquet")

    val dataFrameFromParquet = sparkSession.read.parquet("spark-sql/files/write/persons.parquet")
    dataFrameFromParquet.show()

    sparkSession.stop()

  }

}
