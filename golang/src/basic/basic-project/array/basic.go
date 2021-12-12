package main

import "fmt"

func main() {

	/**
	数组定长后不能使用append()，append()是针对slice的。所以注释中下面这段会报错

	var a [10]int
	for i := 0; i < 100; i++ {
		value  := i * 10 + 1
		a = append(a, value)
	}
	fmt.Println(a)
	 */
	// 没有指定数组的长度时它就是一个切片
	var a []int
	for i := 0; i <30; i++ {
		value  := i * 10 + 1
		a = append(a, value)
	}
	fmt.Println(a)
	// slice bounds out of range [:60] with capacity 32
	//fmt.Println(a[5: 60])

/*	b := [3]int{0, 0, 0}
	var c [2]int = [2]int{3, 9}
	// copy是针对切片的，数组是不能使用copy的。
	copy(b, c)*/

}
