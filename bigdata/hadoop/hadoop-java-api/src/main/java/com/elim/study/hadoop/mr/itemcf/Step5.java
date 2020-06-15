package com.elim.study.hadoop.mr.itemcf;

import com.google.common.collect.Maps;
import org.apache.commons.compress.utils.Lists;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.util.*;

/**
 * 把相乘之后的矩阵按照用户进行汇总，得到结果矩阵（给每个用户的推荐物品）
 */
public class Step5 {

    public static boolean run(Configuration config, Map<String, String> paths) {
        try {
            FileSystem fs = FileSystem.get(config);
            Job job = Job.getInstance(config);
            job.setJobName("step5");
            job.setJarByClass(Step5.class);
            job.setMapperClass(Step5Mapper.class);
            job.setReducerClass(Step5Reducer.class);

            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(Text.class);

            job.setInputFormatClass(KeyValueTextInputFormat.class);

            FileInputFormat
                    .addInputPath(job, new Path(paths.get("Step5Input")));
            Path outpath = new Path(paths.get("Step5Output"));
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
     * 原样输出
     */
    private static class Step5Mapper extends Mapper<Text, Text, Text, Text> {

        @Override
        protected void map(Text key, Text value, Context context) throws IOException, InterruptedException {
            context.write(key, value);//使用的是KeyValueTextInputFormat，所以这里可以直接使用KEY/VALUE
        }

    }

    /**
     * 汇总需要给一个用户进行的物品推荐列表，有需要还可以在写入结果前进行一次排序
     */
    private static class Step5Reducer extends Reducer<Text, Text, Text, Text> {

        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context)
                throws IOException, InterruptedException {
            List<String> items = Lists.newArrayList();
            Map<String, Double> itemMap = Maps.newHashMap();
            for (Text value : values) {
                String[] strs = value.toString().split(":");
                String item = strs[0];
                Double score = Double.parseDouble(strs[1]);
                itemMap.merge(item, score, Double::sum);
            }
            Set<Map.Entry<String, Double>> entries = itemMap.entrySet();
            List<Map.Entry<String, Double>> entryList = new ArrayList<>(entries);
            Collections.sort(entryList, Comparator.comparingDouble(Map.Entry::getValue));
            StringBuilder result = new StringBuilder();
            int max = 10;//按倒序最多取10个
            for (int i = entryList.size() - 1; i >= 0; i--) {
                Map.Entry<String, Double> entry = entryList.get(i);
                result.append(entry.getKey()).append(":").append(entry.getValue()).append(",");
                if (entryList.size() - i == max) {
                    break;
                }
            }
            result.setLength(result.length() - 1);
            context.write(key, new Text(result.toString()));
        }

    }

}
