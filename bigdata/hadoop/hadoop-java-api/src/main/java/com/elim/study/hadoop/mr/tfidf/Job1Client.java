package com.elim.study.hadoop.mr.tfidf;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * TFIDF 用来计算一个词对一个文件的重要程度（一个词在一个文件中出现的次数越多它的重要性越大，但同时它出现的文件数越多重要性越小）
 *
 * Job1计算出总的微博数和每个微博中每个单词的数量
 * 第一个MR，计算TF和计算N(微博总数)
 */
public class Job1Client {

    private static final Logger logger = LoggerFactory.getLogger(Job1Client.class);

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration configuration = new Configuration(true);
        //表示在本地单进程模拟跑，可以直接在本地运行该main方法
        configuration.set("mapreduce.framework.name", "local");
        Job job = Job.getInstance(configuration);
        job.setJobName("TFIDF-Job1");
        job.setJarByClass(Job1Client.class);
        job.setMapperClass(Job1Mapper.class);
        job.setPartitionerClass(Job1Partitioner.class);
        job.setReducerClass(Job1Reducer.class);
        job.setCombinerClass(Job1Reducer.class);//当一个节点有多个Mapper任务时先将它们输出的文件进行归并
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        job.setNumReduceTasks(4);

        Path input = new Path("/user/elim/data/tfidf/input");
        Path output = new Path("/user/elim/data/tfidf/output/job1");
        if (FileSystem.get(configuration).exists(output)) {
            output.getFileSystem(configuration).delete(output, true);
        }
        FileInputFormat.addInputPath(job, input);
        FileOutputFormat.setOutputPath(job, output);

        boolean result = job.waitForCompletion(true);

        logger.info("Job1 running result is {}", result);

    }

}
