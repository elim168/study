package main

import "fmt"

func main() {

	/*
		   输出如下：
		    1
			2
			3
			8
			9
			10
			11
	*/

	a := 10
	fmt.Println(1)
	fmt.Println(2)
	fmt.Println(3)
	if a > 5 {
		// 跳转到指定的label。for循环也可以定义label，然后break label跳出指定的循环
		goto label1
	}
	fmt.Println(4)
	fmt.Println(5)
	fmt.Println(6)
	fmt.Println(7)
label1:
	fmt.Println(8)
	fmt.Println(9)
	fmt.Println(10)
	fmt.Println(11)
}
