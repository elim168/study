package main

import (
	"fmt"
	"time"
)

func main() {

	c1 := make(chan int, 5)

	go func() {
		// 对通道进行迭代接收消息，通道关闭后消息都接收完了就会退出循环
		for msg := range c1 {
			fmt.Println("c1收到了一条消息：", msg)
		}
		// 因为在发送消息的时候发送了5条消息好后对c1进行了关闭，所以对c1的迭代会结束。最终可以执行下面这句。
		fmt.Println("所有消息接收完毕")
	}()

	for i := 0; i < 5; i++ {
		c1 <- i
		fmt.Println("c1发送了一条消息：", i)
	}
	close(c1)

	time.Sleep(time.Second)

}
