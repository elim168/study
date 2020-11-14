package com.elim.study.spark.sql

import org.apache.spark.SparkConf
import org.apache.spark.sql.{SaveMode, SparkSession}

object SparkOnHive {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("SparkOnHiveTest")
    val session = SparkSession.builder().appName("SparkOnHiveTest").enableHiveSupport().getOrCreate()
    import session.sql
    // 指定数据库
    sql("USE TEST")
    // 从Hive的表中查询数据
    val dataFrame = sql("SELECT * FROM PERSON")
    dataFrame.show()
    dataFrame.printSchema()
    // 写入数据到Hive的表中
    dataFrame.write.mode(SaveMode.Overwrite).saveAsTable("spark_hive_person")
    session.stop()
  }

}
