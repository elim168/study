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
 * 打的jar包没有指定main方法时可以通过如下方式运行MapperReduce bin/hadoop jar hadoop-java-api-1.0.0.jar com.elim.study.hadoop.mr.wordcount.WordCount /user/elim/test2.txt <br/>
 *
 * 文件分块后有可能一行数据就落到了两个不同的块上，这样的数据直接使用就是不完整的。默认情况下切片数量和文件的块数量一致。hadoop的做法是除最后一个切片外每个切片都会多拿下一个切片的第一行数据，这样可以保证每一个切片的数据都是完整的。
 * 一个切片就对应于一个Map。切片不能跨文件，但是可以跨文件块。默认情况下切片数量和文件的块数量一致。Map完成后会对Key进行一个排序，还会有一个Combine的过程，其将会把数据进行归并。
 * 在reduce阶段先拿所有Map阶段产生的数据进行归并排序，这样整体的数据就是有序的了。
 *
 * 相同Key的数据为一组调用一次reduce方法。
 *
 *
 *
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
        // 默认是FileInputFormat
//        job.setInputFormatClass(FileInputFormat.class);

        //设置Key分组的比较器，即如何界定哪些Key为一组。默认使用Mapper阶段的快速排序的比较器
//        job.setGroupingComparatorClass(null);

        Path inputPath = new Path("/user/elim/test.txt");
        Path outputPath = new Path("/result/mapreduce/example/wordcount/" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
//        FileSystem fileSystem = outputPath.getFileSystem(job.getConfiguration());
        if (args != null && args.length > 0) {
            inputPath = new Path(args[0]);
            if (args.length > 1) {
                outputPath = new Path(args[1]);
            }
        }

        // 设置输入文件的位置，可以有多个
        FileInputFormat.addInputPath(job, inputPath);
        FileOutputFormat.setOutputPath(job, outputPath);

        // 设置切片的最大值和最小值，默认不设置时分别是无限制和1。
//        FileInputFormat.setMaxInputSplitSize(job, 5);
//        FileInputFormat.setMinInputSplitSize(job, 1);

        // Submit the job, then poll for progress until the job is complete
        job.waitForCompletion(true);

    }

}
