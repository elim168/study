package main

import (
	"fmt"
	"sync/atomic"
)

func main() {

	//counter := uint(0)
	var counter uint64 = 0
	fmt.Println(counter)
	for i := 0; i < 10; i++ {
		atomic.AddUint64(&counter, 1)
	}
	fmt.Println("Counter = ", counter)

	loopCounter := make(chan int)
	for i := 0; i < 100; i++ {
		go func() {
			for j := 0; j < 100; j++ {
				atomic.AddUint64(&counter, 1)
			}
			loopCounter <- 1
		}()
	}

	doneCounter := 0
	for {
		<-loopCounter
		doneCounter++
		if doneCounter == 100 {
			break
		}

	}

	fmt.Println("原子性递增完成，结果为", counter)

	// 用新值替换出旧值
	//oldValue := atomic.SwapUint64(&counter, 1)

	// 设置值
	//atomic.StoreUint64(&counter, 1)
	// 获取值
	//currentValue := atomic.LoadUint64(&counter)
	// 比较和替换值
	//atomic.CompareAndSwapUint64(&counter, old, new)

}
