package com.elim.study.spark.sql.test.function

import org.apache.spark.sql.SparkSession

/**
 * 内置函数有很多，详情可以参考http://spark.apache.org/docs/latest/sql-ref-functions-builtin.html
 */
object BultinFunctions {

  def main(args: Array[String]): Unit = {
    val session = SparkSession.builder().appName("test").master("local").getOrCreate()
    import session.sql

    sql("SELECT avg(col) FROM VALUES (1), (2), (3) AS tab(col)").show()
    sql("SELECT array_contains(array(1, 2, 3), 2)").show()

    session.stop()
  }

}
