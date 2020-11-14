package com.elim.study.spark.sql.test

import java.util.Properties

import org.apache.spark.SparkConf
import org.apache.spark.sql.{SaveMode, SparkSession}


/**
 * 测试把DataFrame的内容通过JDBC写入到数据库中。
 *
 *
 * mysql> show tables;
 * +----------------+
 * | Tables_in_test |
 * +----------------+
 * | spark_persons  |
 * +----------------+
 * 1 row in set (0.00 sec)
 *
 * mysql> select * from spark_persons;
 * +------+----------+------+
 * | age  | name     | sex  |
 * +------+----------+------+
 * |   30 | ZhangSan | F    |
 * |   31 | LiSi     | F    |
 * |   32 | WangWu   | M    |
 * |   33 | ZhaoLiu  | F    |
 * |   34 | TianQi   | M    |
 * |   35 | HuangBa  | F    |
 * |   36 | HeJiu    | F    |
 * |   37 | QianShi  | M    |
 * |   38 | YangYi   | F    |
 * |   39 | TanEr    | M    |
 * +------+----------+------+
 * 10 rows in set (0.00 sec)
 *
 */
object WriteDataFrameToJdbc {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local").setAppName("test")

    val sparkSession = SparkSession.builder().config(conf).getOrCreate()
    // dataFrame类似一张表，底层是Row类型的RDD
    val dataFrame = sparkSession.read.json("spark-sql/files/persons.json")

    val connectionProperties = new Properties()
    connectionProperties.put("user", "root")
    connectionProperties.put("password", "123456")
    connectionProperties.put("driverClass", "com.mysql.jdbc.Driver")

    // 指定的表如果不存在，则会自动进行创建
    dataFrame.write.mode(SaveMode.Overwrite).jdbc("jdbc:mysql://172.18.0.2:3306/test", "spark_persons", connectionProperties);

    // 其也支持指定SaveMode
//    dataFrame.write.mode(SaveMode.Append).jdbc("jdbc:mysql://172.18.0.2:3306/test", "spark_persons", connectionProperties);

    sparkSession.stop()

  }

}
