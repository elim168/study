package main

import (
	"fmt"
	"time"
)

/**
最终的执行结果如下：
no message send
c1没有收到消息
没有任何活动
c1收到了一条消息 Hello
没有任何活动……
no message send
没有任何活动……
没有任何活动……

 */
func main() {

	// 通道的非阻塞收发。依赖select/default组合

	c1 := make(chan string)
	c2 := make(chan string)

	// 非阻塞发消息
	select {
	case c1 <- "Hello C1":
		fmt.Println("c1 发消息成功")
	default:
		fmt.Println("no message send")
	}
	go func() {
		// 非阻塞发消息
		select {
		// 没有缓存区的channel，仅当刚好有接收者在接收时才能发成功。
		case c1 <- "Hello C1 in go":
			fmt.Println("c1 发消息成功")
		default:
			fmt.Println("no message send")
		}
	}()
	// 非阻塞收消息
	select {
	// 没有缓存区的channel，仅当刚好有发送者在发消息才能接收成功。
	case msg := <-c1:
		fmt.Println("c1收到消息：", msg)
	default:
		fmt.Println("c1没有收到消息")
	}

	// 只有这一条同步发的消息被收到了
	go func() {
		c1 <- "Hello"
	}()


	// 多通道一起
	select {
	case c1 <- "Hello C1 Again":
		fmt.Println("C1 发送了一条消息Again")
	case msg := <-c1:
		fmt.Println("c1收到了一条消息", msg)
	case msg := <-c2:
		fmt.Println("c2收到了一条消息", msg)
	default:
		fmt.Println("没有任何活动")
	}

	go func() {
		for {
			// 多通道一起
			select {
			case c1 <- "Hello C1 Again":
				fmt.Println("C1 发送了一条消息Again")
			case msg := <-c1:
				fmt.Println("c1收到了一条消息", msg)
			case msg := <-c2:
				fmt.Println("c2收到了一条消息", msg)
			default:
				fmt.Println("没有任何活动……")
				time.Sleep(time.Millisecond * 100)
			}
		}
	}()

	time.Sleep(time.Second)



}
