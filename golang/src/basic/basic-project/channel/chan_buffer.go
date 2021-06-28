package main

import "fmt"

func main() {

	// 创建一个通道，通道发送的值是string类型。缓冲区是2，表示可以缓冲两条消息，发送两条消息时如果没有监听方收消息则不会阻塞
	msg := make(chan string, 2)

	// 往通道发送消息。默认情况下通道的发和收必须是一起的，收发都是阻塞的，消息发出后必须有接收。有了缓冲区后这里不会阻塞
	msg <- "Hello Chan"
	msg <- "Hello Chan Again"
	// 再多发一条就会形成阻塞，下面的消费的逻辑走不到。程序就会认为存在死锁报错
	//msg <- "Hello Chan Again"

	fmt.Println("收到一条消息:", <- msg)
	fmt.Println("收到一条消息:", <- msg)


}
