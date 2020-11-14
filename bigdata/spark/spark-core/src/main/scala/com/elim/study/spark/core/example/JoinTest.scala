package com.elim.study.spark.core.example

import org.apache.spark.{SparkConf, SparkContext}

/**
 * 二元组类型的RDD可以使用join相关操作
 */
object JoinTest {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[2]").setAppName("Join Test")
    val context = new SparkContext(conf)
    val rdd1 = context.makeRDD(List(("A", 1), ("B", 2), ("C", 3), ("D", 4), ("E", 5)))
    val rdd2 = context.makeRDD(List(("A", 100), ("B", 200), ("C", 300), ("D", 400), ("F", 600)))

    /**
     * join操作只合并Key相同的元素，合并后会把rdd1中的值和rdd2中的值合成一个元组作为新元组的值返回。下面的程序输出如下：
     *
     * (A,(1,100))
     * (C,(3,300))
     * (B,(2,200))
     * (D,(4,400))
     */
    rdd1.join(rdd2).foreach(tuple => {
      println(tuple)
    })


    /**
     * leftOuterJoin会以左边为主。输出如下：
     *
     * (A,(1,Some(100)))
     * (C,(3,Some(300)))
     * (E,(5,None))
     * (B,(2,Some(200)))
     * (D,(4,Some(400)))
     */
    rdd1.leftOuterJoin(rdd2).foreach(println(_))


    /**
     * rightOuterJoin以右边为主。输出如下：
     *
     * (B,(Some(2),200))
     * (F,(None,600))
     * (D,(Some(4),400))
     * (A,(Some(1),100))
     * (C,(Some(3),300))
     */
    rdd1.rightOuterJoin(rdd2).foreach(println(_))


    /**
     * fullOuterJoin会包含所有的元素。输出如下：
     *
     * (A,(Some(1),Some(100)))
     * (C,(Some(3),Some(300)))
     * (E,(Some(5),None))
     * (B,(Some(2),Some(200)))
     * (F,(None,Some(600)))
     * (D,(Some(4),Some(400)))
     */
    rdd1.fullOuterJoin(rdd2).foreach(println(_))


    /**
     * 取交集
     */
    rdd1.intersection(rdd2).foreach(println(_))

    /**
     * 取差集
     */
    rdd1.subtract(rdd2).foreach(println(_))

    /**
     * union
     * 把两个RDD的内容组合起来。
     */
    rdd1.union(rdd2).foreach(println(_))

    /**
     * 去重
     */
    rdd1.distinct()

  }

}
