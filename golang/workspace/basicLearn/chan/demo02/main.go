package main

import "fmt"

// write 函数的入参是一个只写的管道，进行读操作会报错
func write(data chan<- int) {
	// 往管道中写10个数据
	for i := 1; i <= 10; i++ {
		data <- i
		fmt.Println("write()写入了", i)
	}
	close(data)
}

// read有两个参数，第一个参数是只读的管道，第二个参数是只写的管道
func read(data <-chan int, done chan<- bool) {
	for v := range data {
		fmt.Println("read()读取到了", v)
	}
	// 读取完成了就通知主线程
	done <- true
}

func main() {
	data := make(chan int)
	done := make(chan bool)
	go write(data)
	go read(data, done)
	// done在这里读数据会阻塞，直到read()往里面丢了一个数据
	<-done
}
