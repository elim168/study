package main

import "fmt"

// // defer是在函数执行完后，跳出函数前执行，包括异常跳出或者正常return。所以最终的输出如下
/**
B
A
panic: Hello, Error
 */
func defer2_test() {
	defer fmt.Println("A")
	defer fmt.Println("B")
	panic("Hello, Error")
}

func main() {
	defer2_test()
}
