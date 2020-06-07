package com.elim.study.hadoop.mr.weather;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * Mapper每处理一次KEY/VALUE都会调用一次getPartition获取其对应的分区，形成KVP写入文件
 */
public class WeatherPartitioner extends Partitioner<WeatherItem, IntWritable> {
    @Override
    public int getPartition(WeatherItem weatherItem, IntWritable intWritable, int numPartitions) {
        int partition = weatherItem.getYear() % numPartitions;
        return Math.abs(partition);
    }

}
