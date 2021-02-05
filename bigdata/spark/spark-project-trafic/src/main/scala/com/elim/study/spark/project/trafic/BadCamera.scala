package com.elim.study.spark.project.trafic

import java.util

import org.apache.spark.SparkConf
import org.apache.spark.api.java.JavaPairRDD
import org.apache.spark.sql.{Row, SparkSession}

/**
 * 在分布式环境下不能像下面这样简单的计算
 */
object BadCamera {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local").setAppName("BadCamera")
    val sparkSession = SparkSession.builder().config(conf).getOrCreate()
    MockData.mock(sparkSession)

    val factMonitorCameraFrame = sparkSession.sql("select monitor_id,camera_id from monitor_flow_action")
//    factMonitorCameraFrame.cache()
    /**
     * 按照卡扣号分组，对应的数据是：每个卡扣号(monitor)对应的Row信息
     */
    val factMonitorId2RowsRDD: JavaPairRDD[String, java.lang.Iterable[Row]] = factMonitorCameraFrame.toJavaRDD.mapToPair(row => (row.getString(0), row)).groupByKey();

    /**
     * 遍历分组后的RDD，拼接字符串
     * monitor_id=|cameraIds=|camera_count=|carCount=
     * 例如:
     * ("0005","monitorId=0005|camearIds=09200,03243,02435,03232|cameraCount=4|carCount=100")
     *
     */
    val factMonitor: JavaPairRDD[String, String] = factMonitorId2RowsRDD.mapToPair(pair => {
      // 分布式环境下前面已经用了groupByKey，已经把数据进行聚合了，同一个Key的数据只会落到一台机器上执行，所以下面的逻辑是没问题的。
      val monitorId = pair._1
      val cameraIds = new util.ArrayList[String]()
      var carCount = 0
      var cameraIdStr = ""
      pair._2.forEach(row => {
        val cameraId = row.getString(1)
        carCount += 1
        if (!cameraIds.contains(cameraId)) {
          cameraIds.add(cameraId)
          cameraIdStr += s",${cameraId}"
        }
      })
      val result = s"monitorId=${monitorId}|cameraIds=${cameraIdStr.substring(1)}|cameraCount=${cameraIds.size()}|carCount=${carCount}"
      (monitorId, result)
    })


    val monitorCameraFrame = sparkSession.sql("select monitor_id,camera_id from monitor_camera_info")
    /**
     * 按照卡扣号分组，对应的数据是：每个卡扣号(monitor)对应的Row信息
     */
    val monitorId2RowsRDD: JavaPairRDD[String, java.lang.Iterable[Row]] = monitorCameraFrame.toJavaRDD.mapToPair(row => (row.getString(0), row)).groupByKey();
    val monitorData: JavaPairRDD[String, String] = monitorId2RowsRDD.mapToPair(pair => {
      val monitorId = pair._1
      val cameraIds = new util.ArrayList[String]()
      var carCount = 0
      var cameraIdStr = ""
      pair._2.forEach(row => {
        val cameraId = row.getString(1)
        carCount += 1
        if (!cameraIds.contains(cameraId)) {
          cameraIds.add(cameraId)
          cameraIdStr += s",${cameraId}"
        }
      })
      val result = s"monitorId=${monitorId}|cameraIds=${cameraIdStr.substring(1)}|cameraCount=${cameraIds.size()}|carCount=${carCount}"
      (monitorId, result)
    })

    monitorData.leftOuterJoin(factMonitor).foreach(pair => {
      val monitorId = pair._1
      val cameraPair = pair._2
      val cameraInfo = cameraPair._1
      val cameraMap = getCameraMap(cameraInfo)
      // 实际上有监测数据
      if (cameraPair._2.isPresent) {
        val factCameraInfo = cameraPair._2.get()
        val factCameraMap = getCameraMap(factCameraInfo)
        val cameraCount = cameraMap.get("cameraCount")
        val factCameraCount = factCameraMap.get("cameraCount")
        if (cameraCount.equals(factCameraCount)) {
          println(s"卡口-${monitorId}没有坏摄像头${cameraCount}-${factCameraCount}")
        } else {
          println(s"卡口-${monitorId}坏了${cameraCount.toInt - factCameraCount.toInt}个摄像头")
        }
      } else {
        // 实际上没有检测数据
        println(s"卡口-${monitorId}的摄像头都是坏的，共${cameraMap.get("cameraCount")}个坏摄像头，分别是${cameraMap.get("cameraIds")}")
      }
    })

    def getCameraMap(cameraInfo: String) : util.Map[String, String] = {
      val cameraMap = new util.HashMap[String, String]()
      val pairs = cameraInfo.split("\\|")
      pairs.foreach(pair => {
        val kv = pair.split("=")
        cameraMap.put(kv(0), kv(1))
      })
      cameraMap
    }


  }

}
