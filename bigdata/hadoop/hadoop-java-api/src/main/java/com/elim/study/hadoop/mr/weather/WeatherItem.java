package com.elim.study.hadoop.mr.weather;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * 天气数据项，包含了一天的温度相关信息
 */
public class WeatherItem implements WritableComparable<WeatherItem> {

    private int year;
    private int month;
    private int date;
    private int temperature;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    @Override
    public int compareTo(WeatherItem o) {//按年月日升序排列
        int result = Integer.compare(this.year, o.year);
        if (result != 0) {
            return result;
        }
        result = Integer.compare(this.month, o.month);
        if (result != 0) {
            return result;
        }
        return Integer.compare(this.date, o.date);
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(this.year);
        dataOutput.writeInt(this.month);
        dataOutput.writeInt(this.date);
        dataOutput.writeInt(this.temperature);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.year = dataInput.readInt();
        this.month = dataInput.readInt();
        this.date = dataInput.readInt();
        this.temperature = dataInput.readInt();
    }

    @Override
    public String toString() {
        return "WeatherItem{" +
                "year=" + year +
                ", month=" + month +
                ", date=" + date +
                ", temperature=" + temperature +
                '}';
    }
}
