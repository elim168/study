package com.elim.study.hadoop.mr.friendrecommend;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * 两个用户之间如果存在正式的朋友关系，则忽略该数据，否则统计他们共同好友的数量
 */
public class Step1Reducer extends Reducer<Text, IntWritable, Text, IntWritable> {

    private IntWritable outputValue = new IntWritable();

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int count = 0;
        for (IntWritable value : values) {
            if (value.get() == 0) {//两个用户之间有正式的朋友关系，则忽略该组数据
                return;
            }
            count += value.get();//累加两个用户之间共同好友的数量
        }
        outputValue.set(count);
        context.write(key, outputValue);
    }
}
