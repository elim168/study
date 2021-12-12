package main

import "fmt"

// 验证panic defer recover的配合使用

// 内建函数recover允许程序管理恐慌过程中的Go程。
// 在defer的函数中，执行recover调用会取回传至panic调用的错误值，恢复正常执行，停止恐慌过程。
// 若recover在defer的函数之外被调用，它将不会停止恐慌过程序列。在此情况下，或当该Go程不在恐慌过程中时，或提供给panic的实参为nil时，recover就会返回nil。

// test 方法的入参为0时会执行到panic，类似于Java中的抛出异常，它会中断程序的执行。解决办法请看test2()
func test(a int) {

	fmt.Println("输入的参数a=", a)
	if a == 0 {
		panic("panic会中断程序的正常执行。参数a不能为0")
	}

}

// test2 方法的入参为0时会执行到panic，类似于Java中的抛出异常，它会中断程序的执行。解决办法请看test2()
func test2(a int) {

	// 通过defer函数在遇到panic后在defer函数中通过recover()调用可以对panic进行处理，从而上层代码不会感受到panic的存在
	defer func() {
		// recover()可以接收到在调用test2()时出现的panic
		err := recover()
		if err != nil {
			fmt.Println("通过recover()处理了panic，避免了错误进一步的向上层传递。err=", err)
			fmt.Printf("err的类型是：%T \n", err)
		}
	}()

	fmt.Println("输入的参数a=", a)
	if a == 0 {
		panic("panic会中断程序的正常执行。参数a不能为0")
	}

}

func main() {
	// test()中传入参数为0时会执行到panic，类似Java中的抛出异常，它会中断程序的执行。下面的"Main() is running"就不会输出了
	// test(0)

	// test2内部对panic做了处理。程序可以继续执行。下面的"Main() is running"会输出
	test2(0)
	fmt.Println("Main() is running....")
}
