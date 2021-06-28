package main

import "fmt"

func main() {

	// 创建一个通道，通道发送的值是string类型
	msg := make(chan string)

	// 往通道发送消息。默认情况下通道的发和收必须是一起的，收发都是阻塞的，消息发出后必须有接收。所以我们这里需要使用协程来发消息
	go func() {
		msg <- "Hello Chan"
	}()

	msg1 := <- msg

	fmt.Println("收到一条消息:", msg1)


}
