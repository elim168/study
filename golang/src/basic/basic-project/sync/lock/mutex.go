package main

import (
	"fmt"
	"sync"
)

// 不使用atomic，而使用锁来实现递增
func main() {

	mutex := sync.Mutex{}
	num := 0

	counterDown := make(chan int, 3)
	for i := 0; i < 3; i++ {
		counterDown <- i
	}

	for j := 0; j < 3; j++ {
		go func() {
			for i := 0; i < 10000; i++ {
				mutex.Lock()
				num += 1
				mutex.Unlock()
			}
			<-counterDown
		}()
	}
	// 能发完说明上面的三个go都完成了
	for i := 0; i < 3; i++ {
		counterDown <- i
	}

	// 有了mutex后，每次的执行结果都是30000，而如果去掉mutex则几乎每次的执行结果都不是30000
	fmt.Println("执行完成后num的值为：", num)

}
