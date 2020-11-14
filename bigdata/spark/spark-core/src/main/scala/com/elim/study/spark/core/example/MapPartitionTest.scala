package com.elim.study.spark.core.example

import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable.ListBuffer

object MapPartitionTest {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[3]").setAppName("Test")
    val context = new SparkContext(conf)
    val rdd = context.makeRDD(1 until (10), 3)

    /**
     * mapPartition可以按照分区进行映射，这里的rdd一共有三个分区，所以进行mapPartition映射时会映射3次。
     */
    rdd.mapPartitions(iter => {
      iter.foreach(println)
      iter
    }).collect()

    /**
     * mapPartitionsWithIndex可以在按照分区进行映射时把分区的索引带上
     */
    rdd.mapPartitionsWithIndex((index, iter) => {
      var result = ListBuffer[String]()
      while (iter.hasNext) {
        val str = "parition=" + index + ", value=" + iter.next()
        result.append(str)
      }
      result.iterator
    }).foreach(println)
  }

}
