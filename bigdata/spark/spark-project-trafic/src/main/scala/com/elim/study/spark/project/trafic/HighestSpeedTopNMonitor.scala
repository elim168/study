package com.elim.study.spark.project.trafic

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

/**
 * 找出车速最快的前N个卡口
 */
object HighestSpeedTopNMonitor {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local").setAppName("BadCamera")
    val sparkSession = SparkSession.builder().config(conf).getOrCreate()
    MockData.mock(sparkSession)

    val topN = 5
    // 按卡口进行分组计数
    val monitorFrame = sparkSession.sql("select monitor_id,car,speed from monitor_flow_action")

    println(s"Highest Speed Top-$topN is:")
    // 根据卡口分组
    val rdd1 = monitorFrame.rdd.groupBy(_.getString(0))
    // 统计每个卡口车速范围的数量
    val rdd2 = rdd1.map(item => {
      val monitor_id = item._1
      /**
       * 分别统计高速、中速和低速，规定速度在100以上为高速，60-100为中速，60以下为低速
       */
      var high = 0
      var middle = 0
      var low = 0
      item._2.foreach(row => {
        val speed = row.getString(2).toInt
        if (speed >= 100) {
          high += 1
        } else if (speed >= 60) {
          middle += 1
        } else {
          low += 1
        }
      })
      // 车辆信息统计，分别记录高速、中速和低速的数量
      val speedSortKey = new SpeedSortKey(high, middle, low)
      (speedSortKey, monitor_id)
    })

    // 降序排列并取TopN，并进行打印
    rdd2.sortByKey(false).take(topN).foreach(item => {
      val sortKey = item._1
      println(s"MonitorId=${item._2}, high=${sortKey.high}, middle=${sortKey.middle}, low=${sortKey.low}")
    })

  }

}
