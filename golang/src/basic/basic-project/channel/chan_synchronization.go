package main

import (
	"fmt"
	"time"
)

func main() {

	// A、B两个协程是相互协作的，A完成任务后需要通知B。此时可以通过通道来完成这个协作工作

	done := make(chan bool)
	t1 := time.Now()

	go func(done chan bool) {
		fmt.Println("Working...")
		time.Sleep(time.Second)
		fmt.Println("Done!")
		done <- true
	}(done)

	fmt.Println("Main Thread is Waiting ")
	// 接收消息进行等待
	<- done
	fmt.Println("Sub Thread is done")

	t2 := time.Now()
	costTime := t2.Sub(t1)
	fmt.Println("总耗时：", t2.UnixNano() / 1e6, t1.UnixNano() / 1e6, costTime)
}
