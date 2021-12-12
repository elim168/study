package main

import (
	"fmt"
	"time"
)

func main() {

	/*
		select可以让我们在对管道发送/接收时不阻塞
	*/

	// var sendChan chan<- int = make(chan<- int)
	var sendChan = make(chan int)
	// 正常操作时下面这行会阻塞
	// sendChan <- 10

	// 下面的方式发送数据不会阻塞。但是由于发送的时候没有刚好有一个协程在接收，所以还是不会成功发送，只会走到default，然后可以继续往下面走。
	select {
	case sendChan <- 10:
		fmt.Println("发送数据成功")
	default:
		fmt.Println("发送数据失败")
	}

	go func() {
		num := <-sendChan
		fmt.Println("启动的协程接收到了数据：", num)
	}()

	// 休眠一会等待上面接收的协程准备好
	time.Sleep(time.Millisecond * 100)
	// 下面的代码由于刚好有一个协程在接收数据，所以是可以发送成功的。
	select {
	case sendChan <- 10:
		fmt.Println("发送数据成功", 10)
	default:
		fmt.Println("发送数据失败")
	}

	time.Sleep(time.Millisecond * 100)

	chan2 := make(chan int, 100)
	for i := 0; i < 10; i++ {
		chan2 <- i
	}

	for {
		select {
		case num := <-chan2:
			fmt.Println("从chan2中接收到了数据：", num)
		default:
			fmt.Println("从chan2中没有接收到数据。非阻塞式的")
			time.Sleep(time.Millisecond * 1000)
			chan2 <- 100
		}

	}

}
