package com.elim.study.hadoop.mr.friendrecommend;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.util.StringUtils;

import java.io.IOException;

/**
 * 把两个用户之间的共同好友信息拆分为两部分，比如A和B两个用户。需要把B推荐给A，也需要把A推荐给B。
 */
public class Step2Mapper extends Mapper<Object, Text, Step2Key, IntWritable> {

    private Step2Key outputKey = new Step2Key();
    private IntWritable outputValue = new IntWritable();

    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        //张一:张伊	2
//        String str = "张一:张伊\t2";//数据格式如此
        String[] items = StringUtils.split(value.toString(), '\t');
        String[] names = StringUtils.split(items[0], ':');
        int commonFriendCount = Integer.parseInt(items[1]);
        outputKey.setUser1(names[0]);
        outputKey.setUser2(names[1]);
        outputKey.setCommonFriendCount(commonFriendCount);
        context.write(outputKey, outputValue);//写入第一条输出信息

        outputKey.setUser1(names[1]);
        outputKey.setUser2(names[0]);
        context.write(outputKey, outputValue);//写入第二条输出信息

    }
}
