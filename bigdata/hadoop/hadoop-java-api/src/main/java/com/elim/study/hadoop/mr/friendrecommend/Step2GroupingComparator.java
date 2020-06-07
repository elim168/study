package com.elim.study.hadoop.mr.friendrecommend;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 * Reduce阶段对Key进行分组的比较器。
 * 该实现会把相同的user1作为一组数据调用一次reduce方法。
 */
public class Step2GroupingComparator extends WritableComparator {

    public Step2GroupingComparator() {
        super(Step2Key.class, true);
    }

    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        Step2Key item1 = (Step2Key) a;
        Step2Key item2 = (Step2Key) b;
        return item1.getUser1().compareTo(item2.getUser1());
    }

}
