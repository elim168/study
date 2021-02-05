package com.elim.study.spark.stream.examples.kafka

import java.util

import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.{KafkaUtils, LocationStrategies, OffsetRange}
import org.apache.spark.streaming.{Durations, StreamingContext}

/**
 * 参考文档：http://spark.apache.org/docs/latest/streaming-kafka-0-10-integration.html
 */
object CreateRDD {

  def main(args: Array[String]): Unit = {

    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> "172.18.0.2:9092,172.18.0.3:9092,172.18.0.4:9092",
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "group_Test_Spark_Streaming_RDD",
//      "auto.offset.reset" -> "latest",
      "enable.auto.commit" -> (false: java.lang.Boolean)
    )

    val kafkaParams2 = new util.HashMap[String, Object]();
    kafkaParams.foreach(item => kafkaParams2.put(item._1, item._2))
    val conf = new SparkConf().setAppName("UpdateStateByKey").setMaster("local")
    val sc = new SparkContext(conf)
//    val ssc = new StreamingContext(conf, Durations.seconds(5))
//    val topics = Array("topic1")
    val offsetRanges = Array(
      // topic, partition, inclusive starting offset, exclusive ending offset
      OffsetRange("topic1", 0, 0, 100),
      OffsetRange("topic1", 1, 0, 100)
    )


    // 入参参数类型居然是java.util.Map，所以上面需要转换下
    val rdd = KafkaUtils.createRDD[String, String](sc, kafkaParams2, offsetRanges, LocationStrategies.PreferConsistent)

    val result = rdd.flatMap(_.value().split(" ")).map((_, 1)).reduceByKey(_ + _)
    result.foreach(println)
//    result.print()

//    ssc.start()
//    ssc.awaitTermination()
    sc.stop()

  }

}
