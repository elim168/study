package com.elim.study.hadoop.mr.weather;

import org.apache.hadoop.io.RawComparator;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class WeatherGroupingComparator extends WritableComparator {

    public WeatherGroupingComparator() {
        super(WeatherItem.class, true);
    }

    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        WeatherItem item1 = (WeatherItem) a;
        WeatherItem item2 = (WeatherItem) b;
        int result = Integer.compare(item1.getYear(), item2.getYear());
        if (result != 0) {
            return result;
        }
        return Integer.compare(item1.getMonth(), item2.getMonth());
    }
}
