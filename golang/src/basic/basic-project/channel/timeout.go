package main

import (
	"fmt"
	"time"
)

func main() {

	c1 := make(chan string)

	go func() {
		// 模拟复杂的业务操作
		time.Sleep(time.Second * 2)
		c1 <- "Send One Message"
	}()

	select {
	case msg := <-c1:
		fmt.Println("Received One Msg:", msg)
	case <-time.After(time.Second):
		// 一秒内c1没有收到消息则认为前面的调用超时了
		fmt.Println("超时了")
	}

}
