package com.elim.study.hadoop.mr.itemcf;

import org.apache.hadoop.conf.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 商品推荐算法<br/>
 *
 * 原始数据是每个用户对每个商品的动作，每个动作有不同的权值。
 *
 */
public class Client {

    public static void main(String[] args) {
        Configuration configuration = new Configuration();

        configuration.set("mapreduce.app-submission.corss-paltform", "true");
        configuration.set("mapreduce.framework.name", "local");

        //所有mr的输入和输出目录定义在map集合中
        Map<String, String> paths = new HashMap<String, String>();
        paths.put("Step1Input", "/user/elim/data/itemcf/input");
        paths.put("Step1Output", "/user/elim/data/itemcf/output/step1");
        paths.put("Step2Input", paths.get("Step1Output"));
        paths.put("Step2Output", "/user/elim/data/itemcf/output/step2");
        paths.put("Step3Input", paths.get("Step2Output"));
        paths.put("Step3Output", "/user/elim/data/itemcf/output/step3");
        paths.put("Step4Input1", paths.get("Step2Output"));
        paths.put("Step4Input2", paths.get("Step3Output"));
        paths.put("Step4Output", "/user/elim/data/itemcf/output/step4");
        paths.put("Step5Input", paths.get("Step4Output"));
        paths.put("Step5Output", "/user/elim/data/itemcf/output/step5");

//        Step1.run(configuration, paths);//去重
//        Step2.run(configuration, paths);//得到用户对所有相关商品的喜爱程度
//        Step3.run(configuration, paths);//汇总两个物品一起出现的次数
//        Step4.run(configuration, paths);//基于用户关注的商品计算需要给他推荐的其他同现产品及其评分
        Step5.run(configuration, paths);//相乘的结果按用户进行归并，得出对用户的推荐商品及其评分
    }

}
