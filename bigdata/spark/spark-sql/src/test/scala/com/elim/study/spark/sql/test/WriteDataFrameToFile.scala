package com.elim.study.spark.sql.test

import org.apache.spark.SparkConf
import org.apache.spark.sql.{SaveMode, SparkSession}

/**
 * 测试把DataFrame的内容写入到一个文件。
 */
object WriteDataFrameToFile {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local").setAppName("test")

    val sparkSession = SparkSession.builder().config(conf).getOrCreate()
    // dataFrame类似一张表，底层是Row类型的RDD
    val dataFrame = sparkSession.read.json("spark-sql/files/persons.json")

    // 写入到一个csv文件，参数中指定的是目录，而不是目标文件
    dataFrame.write.csv("spark-sql/files/write/persons.csv")

    // 可以指定写入的方式，是追加还是覆盖等。
    dataFrame.write.mode(SaveMode.Overwrite)

    /**
     * 可以写入成多种格式类型的文件
     */
    //    dataFrame.write.jdbc()
//    dataFrame.write.text()
//    dataFrame.write.json()

    sparkSession.stop()
  }

}
