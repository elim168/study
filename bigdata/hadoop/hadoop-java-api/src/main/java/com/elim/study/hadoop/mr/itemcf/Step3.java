package com.elim.study.hadoop.mr.itemcf;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.util.Map;

/**
 * 计算两个物品同时出现的次数（即有多个用户同时关注这两个物品），同现矩阵
 */
public class Step3 {

    public static boolean run(Configuration config, Map<String, String> paths) {
        try {
            FileSystem fs = FileSystem.get(config);
            Job job = Job.getInstance(config);
            job.setJobName("step3");
            job.setJarByClass(Step3.class);
            job.setMapperClass(Step3Mapper.class);
            job.setReducerClass(Step3Reducer.class);

            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(IntWritable.class);

            job.setInputFormatClass(KeyValueTextInputFormat.class);//它会把每行的文本按照TAB键分割为KEY/VALUE
            FileInputFormat.addInputPath(job, new Path(paths.get("Step3Input")));
            Path outpath = new Path(paths.get("Step3Output"));
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
    private static class Step3Mapper extends Mapper<Text, Text, Text, IntWritable> {

        private IntWritable outputValue = new IntWritable(1);

        @Override
        protected void map(Text key, Text value, Context context) throws IOException, InterruptedException {
            //u3244	i469:1,i498:1,i154:1,i73:1,i162:1

            //KEY是用户，VALUE是它喜爱的物品及其程度
            String[] items = value.toString().split(",");
            for (String item : items) {
                for (String item2 : items) {
                    item = item.split(":")[0];
                    item2 = item2.split(":")[0];
                    context.write(new Text(item + ":" + item2), outputValue);
                }
            }
        }
    }

    /**
     * 汇总两个物品一起出现的次数
     */
    private static class Step3Reducer extends Reducer<Text, IntWritable, Text, IntWritable> {

        @Override
        protected void reduce(Text key, Iterable<IntWritable> values, Context context)
                throws IOException, InterruptedException {
            int sum = 0;
            for (IntWritable value : values) {
                sum += value.get();
            }
            context.write(key, new IntWritable(sum));
        }

    }

}
