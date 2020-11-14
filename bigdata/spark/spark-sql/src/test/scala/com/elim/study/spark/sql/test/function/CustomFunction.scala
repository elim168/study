package com.elim.study.spark.sql.test.function

import org.apache.spark.sql.api.java.UDF2
import org.apache.spark.sql.{SparkSession, functions}

/**
 * 自定义函数
 */
object CustomFunction {

  def main(args: Array[String]): Unit = {
    val session = SparkSession.builder().master("local").appName("Test").getOrCreate()

    // (_:Int) + 1
//    session.udf.register("plusOne", (v:Int) => v+1)
    // 注册了一个函数，名叫plusOne
    session.udf.register("plusOne", (_:Int) + 1)

    session.sql("select plusOne(5), plusOne(10)").show()


    // 先定义函数，再进行注册
    val random = functions.udf(() => Math.random())
    session.udf.register("random", random)

    import session.sql
    sql("select random() as random_value").show()

    /**
     * 单独import udf，之后就可以直接使用udf定义函数了
     */
    import functions.udf
    val random2 = udf(() => Math.random())
    session.udf.register("random2", random2)
    sql("select random2() as random2_value").show()

    /*new UDF2[Int, Int, Int] {
      override def call(t1: Int, t2: Int): Int = {
        t1 + t2
      }
    }*/

    session.stop()
  }

}
