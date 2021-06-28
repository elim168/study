package main

import "fmt"

func main() {

	jobs := make(chan int, 5)
	done := make(chan bool)

	go func() {
		for {
			// jobs通道关闭了，且没有收到消息时received为false。
			job, received := <-jobs
			if received {
				fmt.Println("收到了消息", job)
			} else {
				fmt.Println("所有的消息已经接收完成")
				done <- true
			}
		}
	}()

	for i := 0; i < 5; i++ {
		jobs <- i
		fmt.Println("发送了一条消息：", i)
	}
	// 消息发送完成了就关闭通道。接收那一方接收完成了后最后
	close(jobs)

	// 同步等待工作处理完成
	<-done

}
