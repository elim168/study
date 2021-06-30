package main

import (
	"fmt"
	"time"
)

// goroutine是一个轻量级的协程

/*
运行程序最终会看到如下输出，f1和f2是交替输出的。
Main start
Main End
f1------------ 0
f2------------ 0
f1------------ 1
f2------------ 1
f2------------ 2
f1------------ 2
f1------------ 3
f2------------ 3
f2------------ 4
f1------------ 4

*/

func gotest1() {
	f1 := func() {
		for i := 0; i < 5; i++ {
			fmt.Println("f1------------", i)
			time.Sleep(time.Millisecond * 100)
		}
	}

	f2 := func() {
		for i := 0; i < 5; i++ {
			fmt.Println("f2------------", i)
			time.Sleep(time.Millisecond * 100)
		}
	}
	fmt.Println("Main start")
	go f1()
	go f2()
	fmt.Println("Main End")
}

func gotest2() {
	for i := 0; i < 10; i++ {
		go func() {
			// 全部输出A：10。i遍历完10次后变成10了。
			fmt.Println("A:", i)
		}()
	}
	for i := 0; i < 10; i++ {
		go func(i int) {
			fmt.Println("B:", i)
		}(i)
	}
}

func main() {

	//gotest1()
	gotest2()

	// 等待敲一个回车
	fmt.Scanln()

}
