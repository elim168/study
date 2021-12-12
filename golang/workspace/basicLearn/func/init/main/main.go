package main

import "fmt"

// 先初始化全局变量
var num = test()

func test() int {
	fmt.Println("test........")
	return 10
}

// 初始化函数会在全局变量初始化后执行。每个文件都可以有一个init函数，它会在main函数之前执行。
func init() {
	fmt.Println("init..........")
}

/*
最终的输出如下：
test........
init..........
num = 10
*/
func main() {
	fmt.Println("num =", num)
}
