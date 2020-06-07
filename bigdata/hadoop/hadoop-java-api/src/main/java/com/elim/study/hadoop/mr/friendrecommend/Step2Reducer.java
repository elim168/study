package com.elim.study.hadoop.mr.friendrecommend;

import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class Step2Reducer extends Reducer<Step2Key, IntWritable, Text, Text> {

    private Text outputKey = new Text();
    private Text outputValue = new Text();

    private static final Logger logger = LoggerFactory.getLogger(Step2Reducer.class);

    @Override
    protected void reduce(Step2Key key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        List<String> recommendUsers = Lists.newArrayList();
        int i = 0;
        for (IntWritable value : values) {
            logger.info("每一条Key的内容是：key={}", key);
            i++;
            recommendUsers.add(key.getUser2());
            if (i == 3) {//最多取三条
                break;
            }
        }
        String user = key.getUser1();//被推荐朋友的用户
        String recommendUserStr = StringUtils.join(recommendUsers, ",");
        outputKey.set(user);
        outputValue.set(recommendUserStr);
        context.write(outputKey, outputValue);
    }
}
