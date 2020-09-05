package com.elim.study.hbase.mapreduce;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

import java.io.IOException;
import java.util.StringTokenizer;

/**
 * 本示例运行完成后会将单词统计结果写入hbase的表hbase_wordcount中。查看结果会看到表中的如下内容：
 * <pre>
 *     hbase(main):005:0> scan 'hbase_wordcount'
 * ROW                                                                   COLUMN+CELL
 *  Elim                                                                 column=cf1:count, timestamp=2020-09-05T16:31:41.056Z, value=2
 *  Hello                                                                column=cf1:count, timestamp=2020-09-05T16:31:41.056Z, value=4
 *  World                                                                column=cf1:count, timestamp=2020-09-05T16:31:41.056Z, value=2
 *  elim                                                                 column=cf1:count, timestamp=2020-09-05T16:31:41.056Z, value=2
 *  hello                                                                column=cf1:count, timestamp=2020-09-05T16:31:41.056Z, value=4
 *  world                                                                column=cf1:count, timestamp=2020-09-05T16:31:41.056Z, value=2
 * 6 row(s)
 * Took 0.0350 seconds
 * </pre>
 */
public class WordcountTest {

    public static void main(String[] args) throws Exception {
        Configuration configuration = new Configuration();
        // 本地运行
        configuration.set("mapreduce.framework.name", "local");
        configuration.set("mapreduce.app-submission.corss-paltform", "true");
        configuration.set("fs.defaultFS", "hdfs://172.18.0.2:9820");
        configuration.set("hbase.zookeeper.quorum", "hbase-node1,hbase-node2,hbase-node3");

        Job job = Job.getInstance(configuration);
        job.setJarByClass(WordcountTest.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        job.setMapperClass(WordcountMapper.class);

        // Mapper的输入需要来自Hbase时使用下面的语句
//        TableMapReduceUtil.initTableMapperJob();

        // 输出结果到Hbase中的表hbase_wordcount中
        TableMapReduceUtil.initTableReducerJob("hbase_wordcount", WordcountReducer.class, job);

        Path inputPath = new Path("/user/elim/data/test.txt");
        FileInputFormat.addInputPath(job, inputPath);
        job.waitForCompletion(true);
    }

    private static class WordcountMapper extends Mapper<Object, Text, Text, IntWritable> {

        private final static IntWritable one = new IntWritable(1);
        private Text word = new Text();

        @Override
        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            StringTokenizer itr = new StringTokenizer(value.toString());
            while (itr.hasMoreTokens()) {
                word.set(itr.nextToken());
                context.write(word, one);
            }
        }

    }

    private static class WordcountReducer extends TableReducer<Text, IntWritable, ImmutableBytesWritable> {

        /**
         * 列族
         */
        private static final byte[] CF = Bytes.toBytes("cf1");
        /**
         * 列名称
         */
        private static final byte[] COUNT = Bytes.toBytes("count");

        @Override
        protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int count = 0;
            for (IntWritable value : values) {
                count += value.get();
            }
            Put put = new Put(Bytes.toBytes(key.toString()));
            put.addColumn(CF, COUNT, Bytes.toBytes(String.valueOf(count)));
            context.write(null, put);
        }
    }

}
