package com.elim.study.hadoop.mr.tfidf;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.IOException;
import java.io.StringReader;

public class Job1Mapper extends Mapper<Object, Text, Text, IntWritable> {

    private Text outputKey = new Text();
    private IntWritable outputVal = new IntWritable(1);

    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        //3823890210294392	今天我约了豆浆，油条
        String[] v = value.toString().trim().split("\t");

        if (v.length >= 2) {

            String id = v[0].trim();
            String content = v[1].trim();

            StringReader sr = new StringReader(content);
            IKSegmenter ikSegmenter = new IKSegmenter(sr, true);
            Lexeme word = null;
            while ((word = ikSegmenter.next()) != null) {
                String w = word.getLexemeText();
                outputKey.set(w + "_" + id);//输出的Key为单词+微博ID
                context.write(outputKey, outputVal);//用于汇总每个单词在每条微博中的数量
                //今天_3823890210294392	1
            }
            outputKey.set("count");//总的微博数
            context.write(outputKey, outputVal);//用于汇总总的微博数
        } else {
            System.out.println(value.toString() + "-------------");
        }
    }
}
