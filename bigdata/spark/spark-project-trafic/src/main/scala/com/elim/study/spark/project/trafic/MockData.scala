package com.elim.study.spark.project.trafic

import java.nio.file.{Files, Paths}
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util

import org.apache.commons.lang3.StringUtils
import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types.DataTypes
import org.apache.spark.sql.{DataFrame, Row, RowFactory, SaveMode, SparkSession}

import scala.collection.JavaConverters._
import scala.util.Random

/**
 * 模拟数据  数据格式如下：
 *
 * 日期	      卡口ID		     摄像头编号  	车牌号	       拍摄时间	              车速	             道路ID   	   区域ID
 * date	 monitor_id	 camera_id	 car	action_time		speed	road_id		area_id
 *
 * monitor_flow_action
 * monitor_camera_info
 */
object MockData {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local").setAppName("Basic Test")

    val sparkSession = SparkSession.builder().config(conf).getOrCreate()
    mock(sparkSession)
  }

  def mock(spark: SparkSession) = {
    var dataFrame: DataFrame = null
    val monitorFlowPath = "data/monitor_flow_action";
    if (Files.exists(Paths.get(monitorFlowPath))) {
      println("使用了之前已经保存好的数据")
      dataFrame = spark.read.json("data/monitor_flow_action")
    } else {
      println("准备生成新的监控数据")
      import java.util
      val dataList = new util.ArrayList[Row]
      val locations = Array[String]("鲁", "京", "津", "冀", "沪", "晋", "豫", "湘", "粤", "苏")
      val todayStr = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDate.now())
      val random = new Random()
      val sc = spark.sparkContext

      // 模拟1000辆车
      for (i <- 0 until 1000) {
        //模拟车牌号：如：京A00001//模拟车牌号：如：京A00001
        val car = locations(random.nextInt(10)) + (65 + random.nextInt(26)).asInstanceOf[Char] + leftPad(random.nextInt(100000), 5)
        var baseActionTime = todayStr + " " + leftPad(random.nextInt(24), 2)
        /**
         * 这里的for循环模拟每辆车经过不同的卡扣不同的摄像头 数据。
         */
        for (j <- 0 until 300) {
          //模拟每个车辆每被30个摄像头拍摄后 时间上累计加1小时。这样做使数据更加真实。
          if (j % 30 == 0 && j != 0) {
            baseActionTime = todayStr + " " + leftPad((baseActionTime.split(" ")(1).toInt + 1), 2)
          }
          val areaId = leftPad(random.nextInt(8) + 1 , 2) //模拟areaId 【一共8个区域】
          val roadId = random.nextInt(50) + 1 + "" //模拟道路id 【1~50 个道路】
          val monitorId = leftPad(random.nextInt(9) + 1, 4) //模拟9个卡扣monitorId，0补全4位
          val cameraId = leftPad(random.nextInt(20) + 1, 5) //模拟摄像头id cameraId
          val actionTime = baseActionTime + ":" + leftPad(random.nextInt(60), 2) + ":" + leftPad(random.nextInt(60), 2) //模拟经过此卡扣开始时间 ，如：2018-01-01 20:09:10
          val speed = (random.nextInt(260) + 1) + "" //模拟速度
          val row = RowFactory.create(todayStr, monitorId, cameraId, car, actionTime, speed, roadId, areaId)
          dataList.add(row)

        }
      }
      val rdd: RDD[Row] = sc.parallelize(dataList.asScala)
      val cameraFlowSchema = DataTypes.createStructType(Array(
        DataTypes.createStructField("date", DataTypes.StringType, true),
        DataTypes.createStructField("monitor_id", DataTypes.StringType, true),
        DataTypes.createStructField("camera_id", DataTypes.StringType, true),
        DataTypes.createStructField("car", DataTypes.StringType, true),
        DataTypes.createStructField("action_time", DataTypes.StringType, true),
        DataTypes.createStructField("speed", DataTypes.StringType, true),
        DataTypes.createStructField("road_id", DataTypes.StringType, true),
        DataTypes.createStructField("area_id", DataTypes.StringType, true)
      ));


      dataFrame = spark.createDataFrame(rdd, cameraFlowSchema)
      // 可以指定写入的方式，是追加还是覆盖等。
      dataFrame.write.mode(SaveMode.Overwrite)
      dataFrame.write.json("data/monitor_flow_action")
    }

    dataFrame.show(10)

    dataFrame.createOrReplaceTempView("monitor_flow_action")


    val dataList = new util.ArrayList[Row]
    for (i <- 1 until(10)) {
      val monitorId = leftPad(i, 4)
      for (j <- 1 until(25)) {
        val cameraId = leftPad(j, 5)
        val row = RowFactory.create(monitorId, cameraId)
        dataList.add(row)
      }
    }

    val monitorAndCameras = spark.sparkContext.parallelize(dataList.asScala)

    val monitorSchema = DataTypes.createStructType(Array(
      DataTypes.createStructField("monitor_id", DataTypes.StringType, true),
      DataTypes.createStructField("camera_id", DataTypes.StringType, true)
    ));

    val monitorDataFrame = spark.createDataFrame(monitorAndCameras.toJavaRDD(), monitorSchema)
    monitorDataFrame.createOrReplaceTempView("monitor_camera_info")
    monitorDataFrame.show(10)

  }

  def leftPad(value: Any, length: Int): String = {
    StringUtils.leftPad(value.toString, length, "0")
  }

}
