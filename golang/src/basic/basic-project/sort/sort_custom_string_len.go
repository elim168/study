package main

import (
	"fmt"
	"sort"
)

// 自定义排序，按照字符串的长度进行排序。这需要我们自定义一个类型。自定义排序的对象都是slice类型

// ByLength 自定义了一个类型ByLength，它是字符串数组类型的别名。然后我们需要实现sort.Interface接口的相关方法
type ByLength []string

func (b ByLength) Len() int {
	return len(b)
}

// Less 比较i,j两个位置的数据，前者是否小于后者需要排在前面
func (b ByLength) Less(i, j int) bool {
	return len(b[i]) < len(b[j])
}

// Swap 交换i，j两个位置的数据
func (b ByLength) Swap(i, j int) {
	b[i], b[j] = b[j], b[i]
}

func main() {

	a1 := []string {"apple", "banana", "watermelon", "pear", "orange"}
	sort.Sort(ByLength(a1))

	fmt.Println("排好序之后为：", a1)

}
