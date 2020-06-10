package com.elim.study.hadoop.mr.tfidf;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * TFIDF 用来计算一个词对一个文件的重要程度（一个词在一个文件中出现的次数越多它的重要性越大，但同时它出现的文件数越多重要性越小）
 * Job3 记录最终的TFIDF，即每个单词在每篇微博中的重要程度
 */
public class Job3Client {

    private static final Logger logger = LoggerFactory.getLogger(Job3Client.class);

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration configuration = new Configuration(true);
        //表示在本地单进程模拟跑，可以直接在本地运行该main方法
//        configuration.set("mapreduce.framework.name", "local");
        Job job = Job.getInstance(configuration);
        job.setJobName("TFIDF-Job3");
        job.setJar("/home/elim/dev/projects/study/bigdata/hadoop/hadoop-java-api/target/hadoop-java-api-1.0.0.jar");
        job.setJarByClass(Job3Client.class);
        job.setMapperClass(Job3Mapper.class);
        job.setReducerClass(Job3Reducer.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);

        //加载微博总数文件到本地
        job.addCacheFile(new Path("/user/elim/data/tfidf/output/job1/part-r-00003").toUri());
        //加载记录了每个单词的总数文件到本地
        job.addCacheFile(new Path("/user/elim/data/tfidf/output/job2/part-r-00000").toUri());

        Path input = new Path("/user/elim/data/tfidf/output/job1");
        Path output = new Path("/user/elim/data/tfidf/output/job3");
        if (FileSystem.get(configuration).exists(output)) {
            output.getFileSystem(configuration).delete(output, true);
        }
        FileInputFormat.addInputPath(job, input);
        FileOutputFormat.setOutputPath(job, output);

        boolean result = job.waitForCompletion(true);

        logger.info("Job1 running result is {}", result);

    }

}
