package com.elim.study.hadoop.mr.tfidf;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.partition.HashPartitioner;

public class Job1Partitioner extends HashPartitioner<Text, IntWritable> {

    @Override
    public int getPartition(Text key, IntWritable value, int numReduceTasks) {
        if (key.toString().equals("count")) {//如果是记录微博数的KEY则放到最后一个分区
            return numReduceTasks - 1;
        }
        //其它的在剩下的分区数中取模
        return super.getPartition(key, value, numReduceTasks - 1);
    }
}
