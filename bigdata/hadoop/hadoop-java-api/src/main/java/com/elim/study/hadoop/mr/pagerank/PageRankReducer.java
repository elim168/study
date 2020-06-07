package com.elim.study.hadoop.mr.pagerank;

import com.google.common.base.Joiner;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.util.StringUtils;

import java.io.IOException;
import java.util.StringJoiner;

public class PageRankReducer extends Reducer<Text, Text, Text, Text> {

    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        String pageRelation = null;
        double sum = 0;
        for (Text value : values) {
            String valueStr = value.toString();
            if (valueStr.contains(" ")) {//页面关系才包含空格
                pageRelation = valueStr;
            } else {//投票信息
                sum += Double.valueOf(valueStr);
            }
        }
        double newPr = 0.15 / 4 + 0.85 * sum;
        String[] items = StringUtils.split(pageRelation, ' ');
        double oldPr = Double.valueOf(items[0]);
        items[0] = String.valueOf(newPr);//替换原来的pr值
        String outputValue = Joiner.on(' ').join(items);
        double d = newPr - oldPr;//新老PR值的差值
        int j = Double.valueOf(d * 1000).intValue();    //放大1000倍
        j = Math.abs(j);
        context.getCounter(Counter.PageRank).increment(j);//把差值进行累积
        context.write(key, new Text(outputValue));
    }

}
