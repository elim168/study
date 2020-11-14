package com.elim.study.spark.core.example

import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable.ListBuffer

/**
 * 重新分区
 * 可以用来重新分区的有repartition和coalesce两个方法。
 */
object RepartitionTest {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local").setAppName("RepartitionTest")
    val context = new SparkContext(conf)
    val rdd = context.makeRDD(1 until (10), 3)
    assert(rdd.getNumPartitions == 3)

    /**
     * 把rdd中的内容映射出来，方便看出分区信息
     */
    val rdd2 = rdd.mapPartitionsWithIndex((partition, iter) => {
      val list = ListBuffer[String]()
      while(iter.hasNext) {
        list.append("partition=" + partition + ", value=" + iter.next())
      }
      list.iterator
    })

    rdd2.repartition(5).mapPartitionsWithIndex((partition, iter) => {
      val list = ListBuffer[String]()
      while(iter.hasNext) {
        list.append("partition=" + partition + ", value=" + iter.next())
      }
      list.iterator
    }).foreach(println)

    /**
     * 部分输出数据如下。通过输出的数据可以看出原分区的数据再重分区后放到了新的分区中了。
     *
     * partition=1, value=partition=0, value=1
     * partition=2, value=partition=0, value=2
     * partition=2, value=partition=2, value=7
     * partition=3, value=partition=0, value=3
     * partition=3, value=partition=1, value=4
     * partition=3, value=partition=2, value=8
     */

    /**
     * 有两个重载方法的coalesce方法，第一个参数是分区个数，第二个参数是是否需要shuffle，
     * 不需要shuffle时原来同一个分区的数据放到新的分区后也会在同一个分区。需要shuffle时则原来同一个分区的数据会被分散到
     * 多个分区中。
     */
    rdd.coalesce(5)
    rdd.coalesce(5, true)
  }

}
