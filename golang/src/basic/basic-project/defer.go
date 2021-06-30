package main

import "fmt"

/**
本程序的输出如下：
main Start
func1 Start
func1 End
func1 defer3
func1 defer2
func1 defer1
main End
main defer3
main defer2
main defer1
*/
func main() {

	// defer 语句会在func执行完成后再执行。有多个defer时，defer会按声明时的倒序执行

	println("main Start")
	defer println("main defer1")
	defer println("main defer2")
	defer println("main defer3")
	func1()
	println("main End")
	fmt.Println("==========================================")

	/**
	下面这段的输出如下：
	A 1 2 3
	B 10 2 12
	B 10 12 22
	A 1 3 4
	*/
	a, b := 1, 2
	defer calc("A", a, calc("A", a, b))
	a = 10
	defer calc("B", a, calc("B", a, b))
	b = 20
}

func func1() {
	println("func1 Start")
	defer println("func1 defer1")
	defer println("func1 defer2")
	defer println("func1 defer3")
	println("func1 End")
}

func calc(index string, a, b int) int {
	r := a + b
	fmt.Println(index, a, b, r)
	return r
}
