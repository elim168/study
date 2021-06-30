package main

import (
	"fmt"
	"time"
)

// 经测试，对于nil的chan，其在进行收发的时候都会阻塞
func main() {

	var c chan int
	go func() {
		fmt.Println("=======准备发信息==========")
		c <- 10
		fmt.Println("=======信息发送完成==========")
	}()

	go func() {
		aint := <-c
		fmt.Println("收到了信息：", aint)
	}()

	time.Sleep(time.Second * 5)

}
