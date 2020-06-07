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
 * 第一步输出的结果类似如下格式，它表示两个用户之间有3个共同好友：
 * <p>张特:张苏	3</p>
 * 第二步：依据共同好友数量从高到低排序，给出共同好友数量最多的三个人作为推荐好友
 */
public class Step2Client {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration configuration = new Configuration(true);
        Job job = Job.getInstance(configuration);
        job.setJarByClass(Step2Client.class);

        job.setMapperClass(Step2Mapper.class);
        job.setMapOutputKeyClass(Step2Key.class);
        job.setMapOutputValueClass(IntWritable.class);

        job.setReducerClass(Step2Reducer.class);
        job.setGroupingComparatorClass(Step2GroupingComparator.class);

        FileInputFormat.addInputPath(job, new Path("/user/elim/data/friendrecommend/output1"));
        Path outputPath = new Path("/user/elim/data/friendrecommend/output2");
        if (outputPath.getFileSystem(job.getConfiguration()).exists(outputPath)) {
            outputPath.getFileSystem(job.getConfiguration()).delete(outputPath, true);
        }
        FileOutputFormat.setOutputPath(job, outputPath);
        job.waitForCompletion(true);
    }

}
