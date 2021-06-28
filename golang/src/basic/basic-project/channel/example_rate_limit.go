package main

import (
	"fmt"
	"time"
)

/**
通过定时发消息和通道的容量来限速。
 */
func main() {

	requests := make(chan int)
	rateLimiter := make(chan int, 3)

	// 模拟不断的有请求进来
	go func() {
		i := 0
		for {
			i++
			requests <- i
		}
	}()

	go func() {
		ticker := time.NewTicker(time.Millisecond * 1000)
		for {
			// 每个固定的时间可以消耗一条记录
			<-ticker.C
			// 每隔固定的时间往限速器发送一条记录。如果限速器已经满了则会阻塞。（或者反过来，定期收一条，处理任务前先发一条）
			rateLimiter <- 1
		}
	}()
	for request := range requests {
		// 处理前先接收一条rateLimiter消息，如果rateLimiter的消息都被接收完了，则会阻塞在这里，直到有消息为止。
		<-rateLimiter
		fmt.Println(time.Now(), "处理了一个请求-", request)
	}

}
