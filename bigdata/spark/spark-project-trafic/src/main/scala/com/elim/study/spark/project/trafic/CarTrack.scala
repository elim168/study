package com.elim.study.spark.project.trafic

import org.apache.spark.SparkConf
import org.apache.spark.sql.{Encoders, SparkSession}

/**
 * 找出车辆的行驶轨迹
 */
object CarTrack {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local").setAppName("BadCamera")
    val sparkSession = SparkSession.builder().config(conf).getOrCreate()
    MockData.mock(sparkSession)

    // 查出车辆走过的每个卡口的时间。可以直接在SQL后面加 order by action_time根据action_time排序
    val monitorFrame = sparkSession.sql("select monitor_id,car,action_time from monitor_flow_action")

    // 选择20辆车，求这20辆车的轨迹。select的列可以有多列，所以这里的结果是Row类型的，之后再取第一个就是String了
    val cars = monitorFrame.select("car").distinct().take(20).map(_.getString(0))
    cars.foreach(println)
    // 广播20辆车的信息
    val carsBroadcast = sparkSession.sparkContext.broadcast(cars)

    // 默认是升序。
//    monitorFrame.sort("action_time")
    // 如果需要按降序排列需要先取到列Column，再指定Column.desc。
//    monitorFrame.sort(monitorFrame.col("action_time").desc)
    monitorFrame.filter(row => {
      val carCode = row.getString(1)
      carsBroadcast.value.contains(carCode)
    }).rdd.sortBy(_.getString(2)).map(row => (row.getString(1), row)).groupByKey().foreach(item => {
      println(s"${item._1}的路线开始---------------------------------------------")
      item._2.foreach(row => {
        println(row.getString(0), row.getString(1), row.getString(2))
      })
      println(s"${item._1}的路线结束×××××××××××××××××××××××××××××××××××××××××××××")
    })



//    monitorFrame.show()
  }

}
