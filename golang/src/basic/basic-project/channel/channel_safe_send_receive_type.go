package main

import "fmt"

func main() {

	// channel作为方法参数时可以通过参数上限制该通道只能发送消息或者接收消息。

	onlySend := func(msg chan <- string) {
		msg <- "发送了一条消息……"
	}

	onlyReceive := func(msg <- chan string) {
		msgString := <- msg
		fmt.Println("收到了一条消息：", msgString)
	}

	msg := make(chan string)

	go onlySend(msg)
	onlyReceive(msg)


}
