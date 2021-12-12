package main

import "fmt"

func main() {

	// 创建一个名为c1的管道，类型是int，缓冲区是10,即发送数据在10个以内时，如果没有消费者，是不会阻塞的。
	c1 := make(chan int, 10)

	c1 <- 1
	c1 <- 2
	c1 <- 3
	// 上面一共往管道发送了三条数据

	v1 := <-c1
	v2 := <-c1
	v3 := <-c1
	fmt.Println("收到的三条数据分别为：", v1, v2, v3)

	c1 <- 4
	c1 <- 5
	c1 <- 6
	// 可以通过内置的close()关闭管道，管道关闭后就不能再往里面写数据了，但是还可以读数据。写数据会panic
	close(c1)
	// 下面语句会panic
	// c1 <- 7
	v4 := <-c1
	fmt.Println("c1关闭后还可以读取数据，读取到了", v4)

	// 还可以通过如下方式遍历管道。如果管道没有关闭，遍历到管道中没有元素了会阻塞，等待其它协程写数据，但是如果没有其它协程写数据，则会报错。
	// 但是如果管道已经关闭了，则读取完所有数据后就结束了。
	for v := range c1 {
		fmt.Println("遍历管道时读取到了", v)
	}

}