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
 * Job2统计df：词在多少个微博中出现过。
 */
public class Job2Client {

    private static final Logger logger = LoggerFactory.getLogger(Job2Client.class);

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration configuration = new Configuration(true);
        //表示在本地单进程模拟跑，可以直接在本地运行该main方法
        configuration.set("mapreduce.framework.name", "local");
        Job job = Job.getInstance(configuration);
        job.setJobName("TFIDF-Job2");
        job.setJarByClass(Job2Client.class);
        job.setMapperClass(Job2Mapper.class);
        job.setReducerClass(Job2Reducer.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        Path input = new Path("/user/elim/data/tfidf/output/job1");
        Path output = new Path("/user/elim/data/tfidf/output/job2");
        if (FileSystem.get(configuration).exists(output)) {
            output.getFileSystem(configuration).delete(output, true);
        }
        FileInputFormat.addInputPath(job, input);
        FileOutputFormat.setOutputPath(job, output);

        boolean result = job.waitForCompletion(true);

        logger.info("Job1 running result is {}", result);

    }

}
