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
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 把得分矩阵和同现矩阵相乘
 */
public class Step4 {

    public static boolean run(Configuration config, Map<String, String> paths) {
        try {
            FileSystem fs = FileSystem.get(config);
            Job job = Job.getInstance(config);
            job.setJobName("step4");
            job.setJarByClass(Step4.class);
            job.setMapperClass(Step4Mapper.class);
            job.setReducerClass(Step4Reducer.class);

            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(Text.class);

            FileInputFormat.setInputPaths(job,
                    new Path[] { new Path(paths.get("Step4Input1")),
                            new Path(paths.get("Step4Input2")) });
            Path outpath = new Path(paths.get("Step4Output"));
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
     * 输出数据包括两类：
     * 一类是物品2与物品1共同出现的次数；
     * 一类是所有用户对物品1的喜爱程度
     */
    private static class Step4Mapper extends Mapper<LongWritable, Text, Text, Text> {

        private IntWritable outputValue = new IntWritable(1);

        private String flag;// A同现矩阵 or B得分矩阵

        //每个maptask，初始化时调用一次
        @Override
        protected void setup(Context context) throws IOException,
                InterruptedException {
            FileSplit split = (FileSplit) context.getInputSplit();
            flag = split.getPath().getParent().getName();// 判断读的数据集
        }

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] tokens = Pattern.compile("[\t,]").split(value.toString());
            if (flag.equals("step3")) {// 同现矩阵
                //i100:i125	1
                String[] v1 = tokens[0].split(":");
                String itemID1 = v1[0];
                String itemID2 = v1[1];
                String num = tokens[1];
                //A:B 3
                //B:A 3
                Text k = new Text(itemID1);// 以前一个物品为key 比如i100
                Text v = new Text("A:" + itemID2 + "," + num);// A:i109,1

                context.write(k, v);//一个物品与另一个物品一同出现的次数

            } else if (flag.equals("step2")) {// 用户对物品喜爱得分矩阵

                //u26	i276:1,i201:1,i348:1,i321:1,i136:1,
                String userID = tokens[0];
                for (int i = 1; i < tokens.length; i++) {
                    String[] vector = tokens[i].split(":");
                    String itemID = vector[0];// 物品id
                    String pref = vector[1];// 喜爱分数

                    Text k = new Text(itemID); // 以物品为key 比如：i100
                    Text v = new Text("B:" + userID + "," + pref); // B:u401,2

                    context.write(k, v);//一个物品所有用户对它的喜爱程度
                }
            }
        }
    }

    /**
     * 基于用户关注的商品1，计算需要给该用户推荐的与商品1共同出现的其它商品的推荐分数
     */
    private static class Step4Reducer extends Reducer<Text, Text, Text, Text> {

        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context)
                throws IOException, InterruptedException {
            // A同现矩阵 or B得分矩阵
            //某一个物品，针对它和其他所有物品的同现次数，都在mapA集合中
            Map<String, Integer> mapA = new HashMap<String, Integer>();// 和该物品（key中的itemID）同现的其他物品的同现集合// 。其他物品ID为map的key，同现数字为值
            Map<String, Integer> mapB = new HashMap<String, Integer>();// 该物品（key中的itemID），所有用户的推荐权重分数。


            //A  > reduce   相同的KEY为一组
            //value:2类:
            //物品同现A:b:2  c:4   d:8
            //评分数据B:u1:18  u2:33   u3:22
            for (Text line : values) {
                String val = line.toString();
                if (val.startsWith("A:")) {// 表示物品同现数字
                    // A:i109,1
                    String[] kv = val.substring(2).split(",");
                    mapA.put(kv[0], Integer.parseInt(kv[1]));
                    //基于 A,物品同现次数
                } else if (val.startsWith("B:")) {
                    // B:u401,2
                    String[] kv = val.substring(2).split(",");
                    mapB.put(kv[0], Integer.parseInt(kv[1]));
                    try {
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            //KEY是一个物品，比如是物品1

            double result = 0;
            for (Map.Entry<String, Integer> entry : mapA.entrySet()) {
                String item = entry.getKey();//与示例的物品1一同出现的物品，比如是物品2
                Integer sameTimes = entry.getValue();//物品1和物品2一同出现的次数
                for (Map.Entry<String, Integer> entry2 : mapB.entrySet()) {
                    String user = entry2.getKey();//一个与物品1相关的用户
                    Integer score = entry2.getValue();//该用户给物品1的评分
                    result = sameTimes * score;
                    Text k = new Text(user);  //用户ID为key
                    Text v = new Text(item + ":" + result);//基于物品1,其他物品（比如物品2）的同现次数与评分(所有用户对A物品)乘机
                    context.write(k, v);//对于用户A（举例）来说，他买了或关注了物品1,给他推荐的物品2及其分数
                }
            }
        }

    }

}
