package com.elim.study.hadoop.mr.tfidf;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class Job1Reducer extends Reducer<Text, IntWritable, Text, IntWritable> {

    private IntWritable outputValue = new IntWritable();

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int sum = 0;
        for (IntWritable value : values) {
            sum = sum + value.get();
        }
        if (key.equals(new Text("count"))) {
            System.out.println(key.toString() + "___________" + sum);
        }
        outputValue.set(sum);
        context.write(key, outputValue);
    }
}
