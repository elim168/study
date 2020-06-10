package com.elim.study.hadoop.mr.tfidf;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

public class Job3Mapper extends Mapper<Object, Text, Text, Text> {

    // 存放微博总数
    private static Map<String, Integer> cmap = new HashMap<>();
    // 存放df
    private static Map<String, Integer> df = new HashMap<>();

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        System.out.println("******************");
        if (!cmap.isEmpty()) {
            return;
        }

        URI[] ss = context.getCacheFiles();
        if (ss != null) {
            for (int i = 0; i < ss.length; i++) {
                URI uri = ss[i];
                if (uri.getPath().endsWith("part-r-00003")) {// 微博总数
                    Path path = new Path(uri.getPath());//文件已经拉取到本地了，所以按照本地文件进行读取
                    BufferedReader br = new BufferedReader(new FileReader(path.getName()));
                    String line = br.readLine();
                    if (line.startsWith("count")) {
                        String[] ls = line.split("\t");
                        cmap.put(ls[0], Integer.parseInt(ls[1].trim()));
                    }
                    br.close();
                } else if (uri.getPath().endsWith("part-r-00000")) {// 词条的DF
                    Path path = new Path(uri.getPath());
                    BufferedReader br = new BufferedReader(new FileReader(path.getName()));
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] ls = line.split("\t");
                        df.put(ls[0], Integer.parseInt(ls[1].trim()));
                    }
                    br.close();
                }
            }
        }
    }

    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        FileSplit fs = (FileSplit) context.getInputSplit();
        // System.out.println("--------------------");


        if (!fs.getPath().getName().contains("part-r-00003")) {

            //豆浆_3823930429533207	2
            String[] v = value.toString().trim().split("\t");
            if (v.length >= 2) {
                int tf = Integer.parseInt(v[1].trim());// tf值
                String[] ss = v[0].split("_");
                if (ss.length >= 2) {
                    String w = ss[0];
                    String id = ss[1];
                    double s = tf * Math.log(cmap.get("count") / df.get(w));
                    NumberFormat nf = NumberFormat.getInstance();
                    nf.setMaximumFractionDigits(5);
                    context.write(new Text(id), new Text(w + ":" + nf.format(s)));
                }
            } else {
                System.out.println(value.toString() + "-------------");
            }
        }
    }

}
