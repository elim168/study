package com.elim.study.hadoop.mr.tfidf;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

public class Job2Mapper extends Mapper<Object, Text, Text, IntWritable> {

    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        // 获取当前 mapper task的数据片段（split）
        FileSplit fs = (FileSplit) context.getInputSplit();

        if (!fs.getPath().getName().contains("part-r-00003")) {//part-r-00003是微博总数的文件

            //豆浆_3823890201582094	3
            String[] v = value.toString().trim().split("\t");
            if (v.length >= 2) {
                String[] ss = v[0].split("_");
                if (ss.length >= 2) {
                    String w = ss[0];
                    context.write(new Text(w), new IntWritable(1));//输出每个单词在每个文件中的数量
                }
            } else {
                System.out.println(value.toString() + "-------------");
            }
        }
    }
}
