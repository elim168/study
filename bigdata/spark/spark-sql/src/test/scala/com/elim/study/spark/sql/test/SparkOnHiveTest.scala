package com.elim.study.spark.sql.test

import org.apache.spark.SparkConf
import org.apache.spark.sql.{SaveMode, SparkSession}

object SparkOnHiveTest {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("SparkOnHiveTest")
    val session = SparkSession.builder().appName("SparkOnHiveTest").enableHiveSupport().getOrCreate()
    import session.implicits._
    import session.sql
    sql("USE TEST")
    val dataFrame = sql("SELECT * FROM PERSON")
    dataFrame.show()
    dataFrame.printSchema()
    dataFrame.write.mode(SaveMode.Overwrite).saveAsTable("spark_hive_person")
    session.stop()
  }

}
