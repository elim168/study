package com.elim.study.hadoop.mr.friendrecommend;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * 好友推荐客户端<br/>
 * 给定如下好友列表，其中第一个是用户，之后的是ta的好友。
 * <p>张三 张蒙 张一 张特 张典 张苏 张利 张牛 张伦 张金 张伊</p>
 * 第一步：计算出每两个用户之间共同好友的数量
 */
public class Step1Client {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration configuration = new Configuration(true);
        Job job = Job.getInstance(configuration);
        job.setJarByClass(Step1Client.class);

        job.setMapperClass(Step1Mapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        job.setReducerClass(Step1Reducer.class);

        FileInputFormat.addInputPath(job, new Path("/user/elim/data/friendrecommend/input"));
        Path outputPath = new Path("/user/elim/data/friendrecommend/output1");
        if (outputPath.getFileSystem(job.getConfiguration()).exists(outputPath)) {
            outputPath.getFileSystem(job.getConfiguration()).delete(outputPath, true);
        }
        FileOutputFormat.setOutputPath(job, outputPath);
        job.waitForCompletion(true);
    }

}
