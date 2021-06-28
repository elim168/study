package main

import "fmt"

func main() {

	// 定义了一个int类型的数组，更确切的说是一个切片slice，并初始化了元素
	a := []int{1, 2, 3, 4, 5}

	// 定义一个容量为10的数组，没有初始化，则里面的元素都是0
	var b [10]int

	// 数组的长度，容量
	println(len(a), cap(a), fmt.Sprint(a))

	println(fmt.Sprint(b), b[1], b[8], len(b), cap(b))

	b[2] = 3
	b[5] = 30
	println(fmt.Sprint(b))//[0 0 3 0 0 30 0 0 0 0]

	var c = []int{2, 3, 6, 8, 9, 10, 22, 23, 28}
	println(fmt.Sprint(c))
	// 数组可以进行切片，即取数组中的某一段内容，语法类型python，用冒号分隔起始索引，包括前面的不包括后面的
	println(fmt.Sprint(c[1:2]))//[3]
	println(fmt.Sprint(c[5:]))//[10 22 23 28]

	// append之后生成新的对象
	d := append(c, 88, 99, 100)
	println(fmt.Sprint(d))


	// 数组的空值是nil，此时长度和cap容量都是0
	var z []int
	fmt.Println(z, len(z), cap(z))
	if z == nil {
		fmt.Println("nil!")
	}

	// 空数组还是可以append的元素的[1 2 3 4 10]
	var x = append(z, 1, 2, 3, 4, 10)
	println(fmt.Sprint(x))

	// 遍历数组。同时包含索引和值，如果只需要索引可以忽略后面的value变量
	for index, value := range x {
		println(index, value)
	}

	var e = [5]int{1, 2, 3, 4}
	// 声明了数组长度为5,但是只指定了4个元素，则未指定数据的部分为0值。
	fmt.Println(e)//[1 2 3 4 0]



}
