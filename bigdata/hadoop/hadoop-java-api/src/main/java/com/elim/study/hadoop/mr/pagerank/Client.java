package com.elim.study.hadoop.mr.pagerank;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * 对页面的重要程度进行计算。有一个文本文件里面记录着如下这样的数据。比如第一行表示页面A包含页面B/C/D的链接
 * <pre>
 * A	B C D
 * B	D
 * C	B D
 * D	C
 * </pre>
 */
public class Client {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration configuration = new Configuration(true);
        //windows上跑需要加下面的配置，表示跨平台的。
//        configuration.set("mapreduce.app-submission.cross-platform", "true");
        /**
         * Hadoop本地模拟跑，需要有hadoop的配置文件，默认会从classpath根路径下加载core-site.xml和hdfs-site.xml文件，
         * 这里把这两个文件放到了src/main/resources目录下。
         * 如果不是在默认的路径下则需要手动把它们添加到Configuration中。
         * configuration.addResource(new Path("/HADOOP_HOME/conf/core-site.xml"));
         * configuration.addResource(new Path("/HADOOP_HOME/conf/hdfs-site.xml"));
         *
         * 本地的用户可能没有权限访问hdfs上的文件，权限不够，可以通过hdfs的相关命令更改文件访问权限，比如“hdfs dfs -chmod -R 777 /user/elim/data”就把/user/elim/data
         * 目录及其所有子目录的权限都定为所有用户都可访问了。
         */
        //表示在本地单进程模拟跑
        configuration.set("mapreduce.framework.name", "local");
        FileSystem fileSystem = FileSystem.get(configuration);
        int i = 0;
        double d = 0.0001;
        /**
         * 第一次的输出作为第二次的输入。将每次计算的PR值与上一次的PR值比较计算一个差值，汇总所有页面的差值，当差值达到每次阈值则停止
         * 计算
         */
        while (true) {
            configuration.setInt("jobCount", i);
            Job job = Job.getInstance(configuration);
            job.setJarByClass(Client.class);
//        job.setJar("/hdfs/filesystem/jar/location");//指定jar包在hdfs系统的位置
            job.setJobName("pageRank-" + i);

            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(Text.class);
            job.setInputFormatClass(KeyValueTextInputFormat.class);//它会把每行的文本按照TAB键分割为KEY/VALUE
            Path inputPath = new Path("/user/elim/data/pagerank/input");
            if (i > 0) {
                inputPath = new Path("/user/elim/data/pagerank/output/pageRank-" + (i - 1));
            }
            Path outputPath = new Path("/user/elim/data/pagerank/output/pageRank-" + i);
            if (fileSystem.exists(outputPath)) {
                fileSystem.delete(outputPath, true);
            }
            FileInputFormat.addInputPath(job, inputPath);
            FileOutputFormat.setOutputPath(job, outputPath);

            job.setMapperClass(PageRankMapper.class);
            job.setReducerClass(PageRankReducer.class);

            boolean success = job.waitForCompletion(true);
            if (success) {
                //所有页面新老PR值的差集
                long sum = job.getCounters().findCounter(Counter.PageRank).getValue();
                double avgd = sum / 4000.0;
                if (avgd < d) {
                    break;
                }
            }
            i++;
        }

    }

}
