package com.elim.study.spark.project.trafic


/**
 * 速度排序Key，默认按照生序排列，低速的排前面
 * @param high
 * @param middle
 * @param low
 */
case class SpeedSortKey(high: Int, middle: Int, low: Int) extends Comparable[SpeedSortKey] {
  override def compareTo(o: SpeedSortKey): Int = {
    if (this.high != o.high) {
      return this.high - o.high
    }
    if (this.middle != o.middle) {
      return this.middle - o.middle
    }
    this.low - o.low
  }
}
