package com.elim.study.spark.sql.test

import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 把普通的RDD转换为SparkSQL的DataFrame，有了DataFrame就可以进行SQL查询了。
 */
object CreateDataFrameFromRDDWithReflect {

  def main(args: Array[String]): Unit = {
    // 一共main方法只能跑一个SparkContext
    val conf = new SparkConf().setMaster("local").setAppName("Test")
    val context = new SparkContext(conf)
    val personsRDD = context.textFile("spark-sql/files/persons.txt").map(line => {
      val values = line.split(",")
      new Person(values.apply(0), values.apply(1).toInt, values.apply(2))
    })
    personsRDD.foreach(println)

    val session = SparkSession.builder().config(conf).getOrCreate()
    // 这里的第二个参数如果换成了PersonScala会打印不出来想要的schema，如果是纯Scala请参考
    val dataFrame = session.createDataFrame(personsRDD, classOf[Person])
    dataFrame.printSchema()
    dataFrame.show()

    session.stop()

  }


}
