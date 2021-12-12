package main

import (
	"fmt"
	"runtime"
	"time"
)

func main() {

	// 获取CPU个数
	numCPU := runtime.NumCPU()
	// 获取当前在运行的协程的个数
	numGoroutine := runtime.NumGoroutine()
	fmt.Println(numCPU, numGoroutine)

	// 设置使用的最大的CPU个数，返回之前设置的格式
	oldGoMaxProcs := runtime.GOMAXPROCS(1)
	fmt.Println("oldGoMaxProcs", oldGoMaxProcs)

	// 设置了使用的CPU的最大个数为1后，下面的内容还是会交替输出。因为多个协程在单个CPU上执行时会交替的分配到时间片。

	for i := 0; i < 10; i++ {
		go func() {
			fmt.Println("a", i)
		}()
	}

	for i := 0; i < 10; i++ {
		go func(i int) {
			fmt.Println("b", i)
		}(i)
	}
	time.Sleep(time.Second)
}
