package com.elim.study.spark.core.example

import org.apache.spark.{SparkConf, SparkContext}

/**
 * 持久化算子有cache、persist和checkpoint三种
 */
object PersistTest {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local").setAppName("Spark-RDD-Persist-Test")

    val lines = new SparkContext(sparkConf)
      .textFile("spark-core/files/sample.txt")
      .map(_.toLowerCase())

    // 缓存前
    val result = lines.filter(_.startsWith("hello"))
    val s1 = System.currentTimeMillis()

    result.count()
    val e1 = System.currentTimeMillis()

    val s2 = System.currentTimeMillis()
    result.count()
    val e2 = System.currentTimeMillis()

    /**
     * 上面是没有进行persist前，两次count()操作都会追踪到前面的原始RDD进行操作，原始RDD的数据又来自磁盘，即又会从磁盘加载一次数据。
     * 文件很大时第二次count操作也将耗时很长。
     */

    // 将filter()操作后的RDD缓存起来，这个也是一个延时操作，只有进行了Action算子后才会将对应的数据缓存起来。
    result.persist()

    /**
     * cache()的内部就是调用的不带参数的persist()
     *   def cache(): this.type = persist()
     *
     * 而不带参数的persist()调用的是带StorageLevel.MEMORY_ONLY的persist()。
     *   def persist(): this.type = persist(StorageLevel.MEMORY_ONLY)
     *
     * StorageLevel的可选值有下面这些，DISK表示存磁盘，MEMORY表示存内存中，MEMORY_AND_DISK表示内存不够时多余的内容存磁盘，而不是一份数据同时存到内存和磁盘中。
     * val NONE = new StorageLevel(false, false, false, false)
     * val DISK_ONLY = new StorageLevel(true, false, false, false)
     * val DISK_ONLY_2 = new StorageLevel(true, false, false, false, 2)
     * val MEMORY_ONLY = new StorageLevel(false, true, false, true)
     * val MEMORY_ONLY_2 = new StorageLevel(false, true, false, true, 2)
     * val MEMORY_ONLY_SER = new StorageLevel(false, true, false, false)
     * val MEMORY_ONLY_SER_2 = new StorageLevel(false, true, false, false, 2)
     * val MEMORY_AND_DISK = new StorageLevel(true, true, false, true)
     * val MEMORY_AND_DISK_2 = new StorageLevel(true, true, false, true, 2)
     * val MEMORY_AND_DISK_SER = new StorageLevel(true, true, false, false)
     * val MEMORY_AND_DISK_SER_2 = new StorageLevel(true, true, false, false, 2)
     * val OFF_HEAP = new StorageLevel(true, true, true, false, 1)
     *
     */

    val s3 = System.currentTimeMillis()
    // 第一次count时result中没有数据将从磁盘加载后再进行计算，计算完成后会将文件内容缓存在内存中
    result.count()
    val e3 = System.currentTimeMillis()

    val s4 = System.currentTimeMillis()
    // 第二次由于内存中已经缓存了result的数据，将不需要再从源头加载数据了，也就不用从磁盘加载文件了。
    result.count()
    val e4 = System.currentTimeMillis()

    println("times(e1 - s1):" + (e1 - s1))
    println("times(e2 - s2):" + (e2 - s2))
    println("times(e3 - s3):" + (e3 - s3))
    println("times(e4 - s4):" + (e4 - s4))

    /**
     * 用于测试的文件中有300多万行数据时，测试的结果如下。可以明显的看到数据缓存后后面的Action算子计算更快了。
     * times(e1 - s1):2068
     * times(e2 - s2):1067
     * times(e3 - s3):1404
     * times(e4 - s4):47
     */


  }

}
