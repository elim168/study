package com.elim.study.spark.core.example

import org.apache.spark.util.AccumulatorV2
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 自定义累加器
 * Driver程序中定义的count在Executor中累积后在Driver程序中获取不到累加后的值，对应的值能在Executor中获取。
 */
object CustomAccumulatorTest {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local").setAppName("CustomAccumulatorTest")
    val context = new SparkContext(conf)
    val rdd1 = context.parallelize(0 until (10))
    val customAccumulator = new CustomAccumulator()
    val customAccumulator2 = new CustomAccumulator()
    // 注册自定义的累加器
    context.register(customAccumulator)
    // 指定了注册的Accumulator的名称
    context.register(customAccumulator2, "Hello Custom Accumulator")
    // 注册的时候还可以指定一个名字，通过下面的方式可以获取该名字
    println(customAccumulator2.name.get)
    rdd1.map(i => {
      customAccumulator.add(String.valueOf(i))
      println(s"Executor customAccumulator.value=${customAccumulator.value}")
    }).collect()
    // 输出10
    println(s"customAccumulator.value=${customAccumulator.value}")
  }

  class CustomAccumulator extends AccumulatorV2[String, String] {

    var _value:String = null;

    /**
     * Returns if this accumulator is zero value or not. e.g. for a counter accumulator,
     * 0 is zero value; for a list accumulator, Nil is zero value.
     * @return
     */
    override def isZero: Boolean = {
      this._value == null
    }

    /**
     * Creates a new copy of this accumulator.
     * @return
     */
    override def copy(): AccumulatorV2[String, String] = {
      val accumulator = new CustomAccumulator()
      accumulator._value = this._value
      accumulator
    }

    /**
     * Resets this accumulator, which is zero value. i.e. call isZero must return true.
     */
    override def reset(): Unit = {
      this._value = null
    }

    /**
     * Takes the inputs and accumulates.
     * @param v
     */
    override def add(v: String): Unit = {
      if (this.isZero) {
        this._value = v
      } else {
        this._value += '|' + v
      }
    }

    /**
     * Merges another same-type accumulator into this one and update its state, i.e. this should be merge-in-place.
     * @param other
     */
    override def merge(other: AccumulatorV2[String, String]): Unit = {
      this.add(other.value)
    }

    /**
     * Defines the current value of this accumulator
     *
     * @return
     */
    override def value: String = {
      this._value
    }
  }

}
