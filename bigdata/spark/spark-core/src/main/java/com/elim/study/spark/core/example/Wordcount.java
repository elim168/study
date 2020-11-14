package com.elim.study.spark.core.example;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.io.Serializable;
import java.util.Arrays;

public class Wordcount implements Serializable {

    public static void main(String[] args) {
        SparkConf sparkConf = new SparkConf();
        // 本地模式运行
        sparkConf.setMaster("local");
        sparkConf.setAppName("Wordcount-Elim");

        JavaSparkContext sparkContext = new JavaSparkContext(sparkConf);
        JavaRDD<String> lines = sparkContext.textFile("spark-core/files/wordcount.txt");
        /**
         * RDD的每一个类似于flatMap这样的操作在Spark中叫做算子，算子包括数据转换型(Transformation)的和Action型的，数据转换型的结果还是一个RDD。
         * Action类型的算子才会触发数据转换型的算子的计算。
         */
        JavaRDD<String> words = lines.flatMap(line -> Arrays.asList(line.split(" ")).iterator());
        JavaPairRDD<String, Integer> wordPairs = words.mapToPair(word -> new Tuple2(word, 1));
        JavaPairRDD<String, Integer> result = wordPairs.reduceByKey((v1, v2) -> v1 + v2);

        /**
         * 排序
         * 1. 先把Key和Value掉个顺序
         * 2. 按照Key进行排序
         * 3. 输出时再进行反向输出
         */
        result.mapToPair(tuple2 -> tuple2.swap()).sortByKey().foreach(tuple2 -> System.out.println(tuple2._2 + "------" + tuple2._1));
//        result.foreach(tuple2 -> System.out.println(tuple2._1 + "-------" + tuple2._2));

//        Shut down the SparkContext.
        sparkContext.stop();
    }

}




