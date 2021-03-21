package com.elim.study.spark.sql.test

import org.apache.spark.sql.{SQLContext, SparkSession}
import org.apache.spark.{SparkConf, SparkContext}

object BasicTest4ReadJson {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local").setAppName("Basic Test")
    val context = new SparkContext(conf)

    val sparkSession = SparkSession.builder().config(conf).getOrCreate()
    // dataFrame类似一张表，底层是Row类型的RDD
    val dataFrame = sparkSession.read.json("spark-sql/files/persons.json")

    /**
     * 会把内容按照表格列的形式输出：
     * +---+--------+---+
     * |age|    name|sex|
     * +---+--------+---+
     * | 30|ZhangSan|  F|
     * | 31|    LiSi|  F|
     * | 32|  WangWu|  M|
     * | 33| ZhaoLiu|  F|
     * | 34|  TianQi|  M|
     * | 35| HuangBa|  F|
     * | 36|   HeJiu|  F|
     * | 37| QianShi|  M|
     * | 38|  YangYi|  F|
     * | 39|   TanEr|  M|
     * +---+--------+---+
     */
    dataFrame.show()

    /**
     * 输出表描述信息：
     * +-------+------------------+-------+----+
     * |summary|               age|   name| sex|
     * +-------+------------------+-------+----+
     * |  count|                10|     10|  10|
     * |   mean|              34.5|   null|null|
     * | stddev|3.0276503540974917|   null|null|
     * |    min|                30|  HeJiu|   F|
     * |    max|                39|ZhaoLiu|   M|
     * +-------+------------------+-------+----+
     */
    dataFrame.describe().show()

    /**
     * 输出表schema信息：
     *
    root
 |-- age: long (nullable = true)
 |-- name: string (nullable = true)
 |-- sex: string (nullable = true)
     */
    dataFrame.printSchema()

    /**
     * dataFrame.columns可以取到所有的列的名称
     */
    dataFrame.columns.foreach(println)

    /**
     * 查询年龄大于35的人的姓名及他们的年龄
     */
//    dataFrame.select("name", "age").where("age>35").show()
    //等价于
//    dataFrame.select("name", "age").where(dataFrame.col("age").gt(35)).show()

    /**
     * 等价于下面这样。
     * 下面这样是先把dataFrame注册为一个临时视图，当表名，然后使用sqlContext.sql直接写SQL语句
     */
    dataFrame.createOrReplaceTempView("t1")
//    dataFrame.sqlContext.sql("select name,age from t1 where age > 35").show()

    /**
     * 可以通过collect()把查询结果当Array返回，通过collectAsList()把结果当List返回。
     */
//    val rows = dataFrame.collect()
//    rows.foreach(println)

    /**
     * DataFrame转RDD/DataSet
     */
    dataFrame.rdd.foreach(row => println(row))

    context.stop()

    sparkSession.stop()
  }

}
