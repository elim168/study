package main

import (
	"fmt"
	"sort"
)

func main() {

	// sort是内置的包，可以对slice进行排序
	a1 := []string{"a", "f", "x", "o", "t"}
	// 对字符串进行排序
	sort.Strings(a1)
	fmt.Println("排序后的数组是：", a1)

	a2 := []int{8, 10, 2, 16, 13, 15}
	// 对int数组排序
	sort.Ints(a2)
	fmt.Println("排序后的数组是：", a2)

	// 判断数组是否是有序的
	sorted := sort.IntsAreSorted(a2)
	fmt.Println("数组a2是有序的？", sorted)

}
