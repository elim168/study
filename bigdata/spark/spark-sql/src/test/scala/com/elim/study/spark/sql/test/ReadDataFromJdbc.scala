package com.elim.study.spark.sql.test

import java.util.Properties

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession


/**
 * 测试把通过JDBC读取数据到DataFrame中。
 *
 *
 */
object ReadDataFromJdbc {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local").setAppName("test")

    val sparkSession = SparkSession.builder().config(conf).getOrCreate()

    val connectionProperties = new Properties()
    connectionProperties.put("user", "root")
    connectionProperties.put("password", "123456")
    connectionProperties.put("driverClass", "com.mysql.jdbc.Driver")

    // dataFrame类似一张表，底层是Row类型的RDD
    val dataFrame = sparkSession.read.jdbc("jdbc:mysql://172.18.0.2:3306/test", "spark_persons", connectionProperties);

    dataFrame.printSchema()

    dataFrame.show()

    sparkSession.stop()

  }

}
