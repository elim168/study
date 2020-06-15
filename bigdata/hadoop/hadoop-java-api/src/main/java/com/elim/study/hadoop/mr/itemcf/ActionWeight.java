package com.elim.study.hadoop.mr.itemcf;

import java.util.HashMap;
import java.util.Map;

/**
 * 所有动作的权重
 */
public class ActionWeight {

    private static Map<String, Integer> MAPPING = new HashMap<String, Integer>();

    static {
        MAPPING.put("click", 1);
        MAPPING.put("collect", 2);
        MAPPING.put("cart", 3);
        MAPPING.put("alipay", 4);
    }

    public static Integer getWeight(String action) {
        return MAPPING.getOrDefault(action, 0);
    }

}
