package com.elim.study.spark.project.trafic

import java.util
import java.util.Collections

import org.apache.spark.SparkConf
import org.apache.spark.sql.{Row, SparkSession}

import scala.collection.JavaConverters
import scala.collection.JavaConverters._

/**
 * 找出车速最快的前N个卡口中速度最快的前N辆车
 */
object HighestSpeedTopNMonitorTopNCar {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local").setAppName("BadCamera")
    val sparkSession = SparkSession.builder().config(conf).getOrCreate()
    MockData.mock(sparkSession)

    val topN = 5
    val topCar = 30
    // 按卡口进行分组计数
    val monitorFrame = sparkSession.sql("select monitor_id,car,speed,action_time from monitor_flow_action")

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

    // 降序排列并取TopN的monitorId
    val topNMonitor = rdd2.sortByKey(false).map(_._2).take(topN)
    val topNMonitorBroadcast = sparkSession.sparkContext.broadcast(topNMonitor)
    monitorFrame.filter(row => topNMonitorBroadcast.value.contains(row.getString(0))).rdd.groupBy(_.getString(0)).foreach(item => {
      val monitorId = item._1
      val list = new util.ArrayList[Int]()
      val rowList = new util.ArrayList[Row]()
      item._2.foreach(row => {
        var speed = row.getString(2).toInt
        if (list.size < topCar) {
          list.add(speed)
          rowList.add(row)
        } else {
          // 如果当前的速度比某个位置的速度快，则跟那个位置的速度替换，之后继续往后遍历直到有比它小的或遍历结束
          var oldRow = row
          for (i <- 0.to(topCar-1)) {
            if (list.get(i) < speed) {
              val old = list.get(i)
              list.set(i, speed)
              rowList.set(i, oldRow)
              speed = old
              oldRow = rowList.get(i)
            }
          }
        }
      })
      println(s"卡口$monitorId 速度最快的前$topCar 是：")
      rowList.asScala.sortBy(_.getString(2).toInt).reverse.foreach(println)
      println("========================分界线============================")
    })

  }

}
