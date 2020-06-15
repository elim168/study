package com.elim.study.hadoop.mr.itemcf;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.util.Map;

/**
 * 按用户分组，得到用户对所有商品的喜爱程度，得分矩阵
 */
public class Step2 {

    public static boolean run(Configuration config, Map<String, String> paths) {
        try {
            FileSystem fs = FileSystem.get(config);
            Job job = Job.getInstance(config);
            job.setJobName("step2");
            job.setJarByClass(Step2.class);
            job.setMapperClass(Step2Mapper.class);
            job.setReducerClass(Step2Reducer.class);

            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(Text.class);

            FileInputFormat.addInputPath(job, new Path(paths.get("Step2Input")));
            Path outpath = new Path(paths.get("Step2Output"));
            if (fs.exists(outpath)) {
                fs.delete(outpath, true);
            }
            FileOutputFormat.setOutputPath(job, outpath);

            boolean f = job.waitForCompletion(true);
            return f;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 输出每个用户对自己有过动作的每个商品的喜爱程度
     */
    private static class Step2Mapper extends Mapper<LongWritable, Text, Text, Text> {

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            //i161,u2625,click,2014/9/18 15:03
            String[] values = value.toString().split(",");
            String item = values[0];
            String user = values[1];
            Integer weight = ActionWeight.getWeight(values[2]);
            context.write(new Text(user), new Text(item + ":" + weight));
        }
    }

    /**
     * 输出每个用户对自己关联的所有商品的喜爱程度
     */
    private static class Step2Reducer extends Reducer<Text, Text, Text, Text> {

        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context)
                throws IOException, InterruptedException {
            StringBuilder items = new StringBuilder();//拼接一个用户的所有相关的商品信息
            for (Text value : values) {
                items.append(value.toString()).append(",");
            }
            items.setLength(items.length() - 1);//去掉最后一个逗号
            context.write(key, new Text(items.toString()));
        }

    }

}
