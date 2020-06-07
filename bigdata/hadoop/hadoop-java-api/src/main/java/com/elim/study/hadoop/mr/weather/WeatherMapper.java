package com.elim.study.hadoop.mr.weather;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;

public class WeatherMapper extends Mapper<Object, Text, WeatherItem, IntWritable> {

    private static final Logger logger = LoggerFactory.getLogger(WeatherMapper.class);

    private WeatherItem outKey = new WeatherItem();
    private IntWritable outValue = new IntWritable();

    public static void main(String[] args) {
        String line = "2010-02-03 08:20:30\t28\n";
        String lineAll = "2010-02-03 08:20:30\t28\n" +
                "2010-02-03 08:20:30     28\n" +
                "2010-02-03 12:20:30     32\n" +
                "2015-08-23 08:20:30     37\n" +
                "2012-02-03 08:20:30     25\n" +
                "2014-05-09 08:20:30     30\n" +
                "2010-02-03 08:20:30     32\n" +
                "2010-05-03 08:20:30     29\n" +
                "2010-06-03 08:20:30     30\n" +
                "2010-07-03 08:20:30     32\n" +
                "2010-08-03 08:20:30     26\n" +
                "2010-09-03 08:20:30     29\n" +
                "2010-10-03 08:20:30     30\n" +
                "2010-11-03 08:20:30     31\n" +
                "2010-12-03 08:20:30     32\n" +
                "2010-01-03 08:20:30     32\n" +
                "2010-02-03 08:20:30     29\n" +
                "2010-03-03 08:20:30     30\n" +
                "2010-04-03 08:20:30     31\n" +
                "2010-05-03 08:20:30     30";
    }

    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        logger.info("mapper解析一行数据，KEY={},VALUE={}", key, value);
        //每行数据格式为yyyy-MM-dd HH:mm:ss    temperature
        String[] dateAndTemperature = StringUtils.split(value.toString(), '\t');
        TemporalAccessor temporalAccessor = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").parse(dateAndTemperature[0]);
        outKey.setYear(temporalAccessor.get(ChronoField.YEAR));
        outKey.setMonth(temporalAccessor.get(ChronoField.MONTH_OF_YEAR));
        outKey.setDate(temporalAccessor.get(ChronoField.DAY_OF_MONTH));
        outKey.setTemperature(Integer.valueOf(dateAndTemperature[1]));

        outValue.set(outKey.getTemperature());

        context.write(outKey, outValue);

    }
}
