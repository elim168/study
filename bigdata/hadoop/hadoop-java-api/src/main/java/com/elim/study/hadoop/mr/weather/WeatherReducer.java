package com.elim.study.hadoop.mr.weather;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 取每个月温度最大的两天。<br/>
 * 一天的温度可能有多个。
 */
public class WeatherReducer extends Reducer<WeatherItem, IntWritable, Text, IntWritable> {

    private static final Logger logger = LoggerFactory.getLogger(WeatherReducer.class);

    private Text outputKey = new Text();
    private IntWritable outputValue = new IntWritable();

    @Override
    protected void reduce(WeatherItem key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        /**
         * 相同的Key为一组调用一次reduce。Key的分组这里使用了自定义的分组比较器{@link WeatherGroupingComparator}，它会把相同的年月作为一组，迭代Value时Key的值
         * 也会跟着变化。即如果有两条数据，一条是属于2020-02-03,一条是属于2020-02-04,虽然它们属于同一组调用一次reduce，但是第一次遍历Value时Key为2020-02-03,第二次为
         * 2020-02-04。这是内部在进行Value迭代时会通过反序列化给WeatherItem赋值。具体可以参考{@link org.apache.hadoop.mapreduce.task.ReduceContextImpl}
         *
         */

        logger.info("------reduce start-------");
        int i = 0;
        int day = 0;
        for (IntWritable value : values) {
            if (i == 0) {
                i++;
                day = key.getDate();//记录温度最高的那一天的日期
                outputKey.set(key.toString());
                outputValue.set(value.get());
                context.write(outputKey, outputValue);
            } else if (key.getDate() != day) {//换天了
                outputKey.set(key.toString());
                outputValue.set(value.get());
                context.write(outputKey, outputValue);
                break;//取到了两条就退出循环
            }
            logger.info("Key is {}, value is {}", key, value);
        }
        logger.info("------reduce end************-------");
    }
}
