package com.elim.study.hadoop.mr.wordcount;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;


/**
 * 打的jar包没有指定main方法时可以通过如下方式运行MapperReduce bin/hadoop jar hadoop-java-api-1.0.0.jar com.elim.study.hadoop.mr.wordcount.WordCount /user/elim/test2.txt
 */
public class WordCount {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        System.out.println("=================" + Arrays.toString(args));

        // Create a new Job
        Job job = Job.getInstance();
        job.setJarByClass(WordCount.class);

        // Specify various job-specific parameters
        job.setJobName("myjob-wordcount-elim");

        job.setMapperClass(WordCountMapper.class);
        job.setReducerClass(WordCountReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
//        job.setCombinerClass();

        Path inputPath = new Path("/user/elim/test.txt");
        Path outputPath = new Path("/result/mapreduce/example/wordcount/" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
//        FileSystem fileSystem = outputPath.getFileSystem(job.getConfiguration());
        if (args != null && args.length > 0) {
            inputPath = new Path(args[0]);
            if (args.length > 1) {
                outputPath = new Path(args[1]);
            }
        }

        FileInputFormat.addInputPath(job, inputPath);
        FileOutputFormat.setOutputPath(job, outputPath);

        // Submit the job, then poll for progress until the job is complete
        job.waitForCompletion(true);

    }

}
