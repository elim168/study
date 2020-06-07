package com.elim.study.hadoop.mr.friendrecommend;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.util.StringUtils;

import java.io.IOException;

public class Step1Mapper extends Mapper<Object, Text, Text, IntWritable> {

    private Text outputKey = new Text();
    private IntWritable outputValue = new IntWritable();

    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        //张三 张蒙 张一 张特 张典 张苏 张利 张牛 张伦 张金 张伊
        String[] names = StringUtils.split(value.toString(), ' ');
        String user = names[0];
        //第一个是用户自己，所以从第2个开始遍历，每两个用户之间都有第一个用户是共同好友。
        for (int i=1; i<names.length; i++) {
            outputKey.set(this.getOutputKey(user, names[i]));
            outputValue.set(0);//直接好友关系
            context.write(outputKey, outputValue);
            for (int j=i+1; j<names.length; j++) {
                outputKey.set(this.getOutputKey(names[i], names[j]));
                outputValue.set(1);//之后的每两个用户之间是间接的朋友关系
                context.write(outputKey, outputValue);
            }
        }
    }

    /**
     * 确保两个相同的名称可以按照同样的顺序排列
     * @param name1
     * @param name2
     * @return
     */
    private String getOutputKey(String name1, String name2) {
        if (name1.compareTo(name2) < 0) {
            return name1 + ":" + name2;
        }
        return name2 + ":" + name1;
    }

}
