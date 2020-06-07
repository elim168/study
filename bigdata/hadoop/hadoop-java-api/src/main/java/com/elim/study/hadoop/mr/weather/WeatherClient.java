package com.elim.study.hadoop.mr.weather;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * 天气客户端
 * 需求：
 *  给一个文件，里面记录了某一天的某个时间的温度，格式为“yyyy-MM-dd HH:mm:ss\ttemperature”，如“2008-01-01 10:20:30  36”。
 *  简单起见，给定的温度都是整数。
 *  现需要统计每个月温度最高的两天，及它们的温度。
 *
 * 实现思路：
 *  1.解析出每行的数据，拆分出日期和温度
 *  2.先按年/月排序，把相同的年月的数据排到一起，年月之后按温度进行倒序排列
 *  3.Reduce时第一条肯定是对应年月的温度最高的一天，但第二条不一定是，因为它可能跟第一条是一天。此时需要依次取，直到取到跟第一条不是同一天的数据。
 */
public class WeatherClient {

    public static void main(String[] args) throws Exception {

        System.out.println("Weather Client Ready To Run =================================");

        Configuration configuration = new Configuration(true);
        Job job = Job.getInstance(configuration);
        job.setJobName("count highest two temperature");
        job.setJarByClass(WeatherClient.class); //至关重要，测试时缺少了这句导致报找不到WeatherMapper类

        //Mapper配置
//        job.setInputFormatClass(null);
        job.setMapperClass(WeatherMapper.class);
        job.setMapOutputKeyClass(WeatherItem.class);
        job.setMapOutputValueClass(IntWritable.class);
        job.setPartitionerClass(WeatherPartitioner.class);
        job.setSortComparatorClass(WeatherItemSortComparator.class);
//        job.setCombinerClass(null);

        //Reduce配置
        job.setGroupingComparatorClass(WeatherGroupingComparator.class);
        job.setReducerClass(WeatherReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        job.setNumReduceTasks(2);

        FileInputFormat.addInputPath(job, new Path("/user/elim/data/weather/input"));
        Path outputPath = new Path("/user/elim/data/weather/output");
        if (outputPath.getFileSystem(job.getConfiguration()).exists(outputPath)) {
            outputPath.getFileSystem(job.getConfiguration()).delete(outputPath, true);
        }
        FileOutputFormat.setOutputPath(job, outputPath);

        job.waitForCompletion(true);

    }

}
