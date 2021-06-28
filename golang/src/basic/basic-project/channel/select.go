package main

import (
	"fmt"
	"time"
)

func main() {

	// 多路复用。如下创建了两个通道
	c1 := make(chan string)
	c2 := make(chan string)

	// c1发送一条消息
	go func() {
		time.Sleep(time.Second)
		c1 <- "Hello Channel 1"
	}()

	// c2发送一条消息
	go func() {
		time.Sleep(time.Second)
		c2 <- "Hello Channel 2"
	}()

	// 多路复用。select可以同时处理两个通道的消息。没有收到消息时会阻塞
	for i := 0; i < 2; i++ {
		fmt.Println("准备接收消息：", i)
		select {
		case msg := <-c1:
			fmt.Println("c1收到一条消息：", msg)
		case msg := <-c2:
			fmt.Println("c2收到一条消息：", msg)
		}
	}

}
