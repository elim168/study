package main

import "fmt"

func main() {
	var i interface{} = "hello"

	// 断言变量i底层对应的值是string类型，并把底层的string值赋给变量s。但是如果底层的类型不是string则会抛异常
	s := i.(string)
	fmt.Println(s)

	// 用两个参数接收，如果底层的值能转换为string类型，则ok为true并返回底层的值赋给变量s，否则ok为false
	s, ok := i.(string)
	fmt.Println(s, ok)

	f, ok := i.(float64)
	fmt.Println(f, ok)

	f = i.(float64) // 报错(panic)
	fmt.Println(f)
}