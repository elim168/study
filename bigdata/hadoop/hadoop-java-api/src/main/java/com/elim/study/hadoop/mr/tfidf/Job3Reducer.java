package com.elim.study.hadoop.mr.tfidf;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class Job3Reducer extends Reducer<Text, Text, Text, Text> {

    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        StringBuffer sb = new StringBuffer();

        for (Text value : values) {
            sb.append(value.toString() + "\t");
        }

        context.write(key, new Text(sb.toString()));//每偏微博中每个单词的重要性
    }
}
