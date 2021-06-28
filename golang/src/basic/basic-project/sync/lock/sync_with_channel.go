package main

import (
	"fmt"
	"time"
)

// 通过通道来实现同步，确保读写操作都由同一个通道来完成





func main() {

	value := 0
	writeChannel := make(chan int)
	done := false

	go func() {
		for {
			select {
			case <-writeChannel:
				value += 1
			}
			if done {
				break
			}
		}
	}()


	counterDownLatch := make(chan int, 100)
	for i := 0; i < 100; i++ {
		go func() {
			for j := 0; j < 100000; j++ {
				writeChannel <- 0
			}
			counterDownLatch <- 0
		}()
	}

	for i := 0; i < 100; i++ {
		<-counterDownLatch
	}
	done = true
	time.Sleep(time.Millisecond * 100)
	// 只有一个通道负责更新值，最后的值就是100 * 100000 = 10,000,000
	fmt.Println("最后的值为：", value)

}
