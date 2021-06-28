package main

import "fmt"

// 可变参数的函数

func print(args ...int)  {
	fmt.Println("Variable Args is", args)
	// args也可以进行遍历
	fmt.Println("args len is", len(args))
	for i,v := range args {
		fmt.Printf("第%d个参数是%d \n", i + 1, v)
	}
}

func fun1(name string, args ...int) {
	fmt.Println(name, args)
}


func main() {
	print(1, 2, 3, 4, 5, 7)
	fun1("ZhangSan", 6, 6, 8)
	// 可变参数也可以直接传递一个数组
	fun1("ZhangSan", []int{1, 2, 3, 4, 5}...)
	a1 := []int{5, 6, 7, 8, 9, 10}
	fun1("LiSi", a1...)
}
