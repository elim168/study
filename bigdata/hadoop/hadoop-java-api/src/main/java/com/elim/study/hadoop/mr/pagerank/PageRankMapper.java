package com.elim.study.hadoop.mr.pagerank;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.util.StringUtils;

import java.io.IOException;

/**
 * 因为我们使用的输入格式化类是{@link org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat}，其会把每行的数据按照TAB键切割为KEY和VALUE
 * 进行传递，所以这里我们Mapper的输入的KEY和VALUE都是Text类型。
 */
public class PageRankMapper extends Mapper<Text, Text, Text, Text> {

    @Override
    protected void map(Text key, Text value, Context context) throws IOException, InterruptedException {
        //从配置中获取之前跑job时传递进去的变量
        int jobCount = context.getConfiguration().getInt("jobCount", 0);
        if (jobCount == 0) {//第一个Job，输入的KEY和VALUE为原始的页面关系
            String valueStr = "1.0 " + value.toString();//第一次附加上原始页面的PR值
            value.set(valueStr);
        }
        //非第一次，进来的Value为“0.5 A B”这样的数据
        context.write(key, value);//原样输出之前的页面关系信息
        String[] values = StringUtils.split(value.toString(), ' ');
        double prValue = Double.valueOf(values[0]);
        double voteValue = prValue / (values.length - 1);
        Text outValue = new Text(String.valueOf(voteValue));
        for (int i = 1; i < values.length; i++) {//之后的是页面关系，给每个页面投一票
            context.write(new Text(values[i]), outValue);
        }
    }

}
