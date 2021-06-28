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

func main() {

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

	// 等待敲一个回车
	fmt.Scanln()


}
