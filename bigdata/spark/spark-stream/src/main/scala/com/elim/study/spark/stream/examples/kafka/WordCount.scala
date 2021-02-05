package com.elim.study.spark.stream.examples.kafka

import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.kafka010.{CanCommitOffsets, ConsumerStrategies, HasOffsetRanges, KafkaUtils, OffsetRange}
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.{Durations, StreamingContext}

/**
 * 参考文档：http://spark.apache.org/docs/latest/streaming-kafka-0-10-integration.html
 */
object WordCount {

  def main(args: Array[String]): Unit = {

    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> "172.18.0.2:9092,172.18.0.3:9092,172.18.0.4:9092",
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "group_Test_Spark_Streaming",
      // 没有初始的offset或当前的offset不存在时如何处理数据：earliest->重置offset为最小值，latest->重置offset为最大值
      "auto.offset.reset" -> "latest",
      "enable.auto.commit" -> (false: java.lang.Boolean)
    )

    val conf = new SparkConf().setAppName("UpdateStateByKey").setMaster("local")
    val ssc = new StreamingContext(conf, Durations.seconds(5))
    val topics = Array("topic1")
    val stream = KafkaUtils.createDirectStream[String, String](
      ssc,
      PreferConsistent,
      Subscribe[String, String](topics, kafkaParams)
    )
    val result:DStream[(String, Int)] = stream.flatMap(_.value().split(" ")).map((_, 1)).reduceByKey(_ + _)
    result.print()

    // 异步的更新消费的offset
    stream.foreachRDD(rdd => {
      val offsetRanges:Array[OffsetRange] = rdd.asInstanceOf[HasOffsetRanges].offsetRanges
      stream.asInstanceOf[CanCommitOffsets].commitAsync(offsetRanges)
      println("=======================================================" + rdd + "=======" + offsetRanges)
      offsetRanges.foreach(item => {
        println("************" + item)
      })
    })
    ssc.start()
    ssc.awaitTermination()

    /**
     * 手动维护offset方法二：
     * // 初次启动时，读取各分区已经读取到的位置，比如从redis中读取等。
     * // 定义各分区读取的offset位置
     * val topicOffsets = Map[TopicPartition, Long](
     * new TopicPartition("topic1", 0) -> 100,
     * new TopicPartition("topic1", 1) -> 100,
     * new TopicPartition("topic1", 2) -> 100,
     * new TopicPartition("topic1", 3) -> 100
     * )
     * val stream1 = KafkaUtils.createDirectStream(ssc, PreferConsistent, ConsumerStrategies.Assign(topicOffsets.keys, kafkaParams, topicOffsets))
     * stream1.foreachRDD(rdd => {
     * val offsetRanges:Array[OffsetRange] = rdd.asInstanceOf[HasOffsetRanges].offsetRanges
     *       offsetRanges.foreach(offsetRange => {
     * println(offsetRange.topic, offsetRange.partition, offsetRange.untilOffset)
     * })
     * // 存起来已经消费的offset，比如存到redis中
     * })
     */

  }

}
