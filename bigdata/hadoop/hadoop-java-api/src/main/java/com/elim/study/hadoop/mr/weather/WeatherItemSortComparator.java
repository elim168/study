package com.elim.study.hadoop.mr.weather;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 * 按年月的正序、温度的倒序排列
 */
public class WeatherItemSortComparator extends WritableComparator {

    public WeatherItemSortComparator() {
        super(WeatherItem.class, true);//指定父类构造方法的KEY的类型。
    }

    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        WeatherItem item1 = (WeatherItem) a;
        WeatherItem item2 = (WeatherItem) b;
        int result = Integer.compare(item1.getYear(), item2.getYear());
        if (result != 0) {
            return result;
        }
        result = Integer.compare(item1.getMonth(), item2.getMonth());
        if (result != 0) {
            return result;
        }
        return Integer.compare(item2.getTemperature(), item1.getTemperature());//温度倒序
    }
}
