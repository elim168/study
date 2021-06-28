package main

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

}

func func1() {
	println("func1 Start")
	defer println("func1 defer1")
	defer println("func1 defer2")
	defer println("func1 defer3")
	println("func1 End")
}
