package main

import "fmt"

func main() {
	defer4_1()
	/**
	输出如下：
	defer4_1开始
	call defer4_2
	defer defer4_1
	*/
}

func defer4_1() int {
	fmt.Println("defer4_1开始")
	defer fmt.Println("defer defer4_1")
	// defer4_2()会比上面的defer先执行。
	return defer4_2()
}

func defer4_2() int {
	fmt.Println("call defer4_2")
	return 10
}