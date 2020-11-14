package com.elim.study.spark.sql.test

import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 把普通的RDD转换为SparkSQL的DataFrame，有了DataFrame就可以进行SQL查询了。
 * 基于Struct来创建DataFrame
 */
object CreateDataFrameFromRDDWithStruct {

  def main(args: Array[String]): Unit = {
    // 一共main方法只能跑一个SparkContext
    val conf = new SparkConf().setMaster("local").setAppName("Test")
    val context = new SparkContext(conf)
    val personsRDD = context.textFile("spark-sql/files/persons.txt").map(_.split(",")).map(fields => Row(fields(0), fields(1).trim.toInt, fields(2)))
    // 定义了Schema，一共包含3个字段，顺序是name、age、sex
    //    val schema = StructType(
    //      StructField("name", StringType, false) ::
    //        StructField("age", IntegerType, false) ::
    //        StructField("sex", StringType) :: Nil)

    // StructField的第三个参数表示是否可为空，默认为true。还可以有第四个参数Metadata
    val schema = StructType(
      Array(StructField("name", StringType, false) ,
        StructField("age", IntegerType, false) ,
        StructField("sex", StringType) ))

    val session = SparkSession.builder().config(conf).getOrCreate()
    val dataFrame = session.createDataFrame(personsRDD, schema)
    dataFrame.printSchema()
    dataFrame.show()

    session.stop()
  }

}
