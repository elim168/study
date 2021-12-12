package main

import "fmt"

func cal(fun MyFuncType, a, b int) int {
	return fun(a, b)
}

// 自定义一个类型，它是一个函数。它可以方便的作为函数的入参和出参。
type MyFuncType func(int, int) int

func main() {
	var addFunc = func(a, b int) int {
		return a + b
	}
	res := cal(addFunc, 10, 20)
	fmt.Println(res)

}
