package com.elim.study.spark.project.trafic

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

/**
 * 找出车流量最大的前N个卡口
 */
object TopNMonitor {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local").setAppName("BadCamera")
    val sparkSession = SparkSession.builder().config(conf).getOrCreate()
    MockData.mock(sparkSession)

    val topN = 8
    // 按卡口进行分组计数
    val monitorFrame = sparkSession.sql("select monitor_id,count(1) from monitor_flow_action group by monitor_id")

    println(s"Top-$topN is:")
    monitorFrame.rdd.sortBy(_.getLong(1), ascending = false).take(topN).foreach(row => {
      println(row)
    })

  }

}
