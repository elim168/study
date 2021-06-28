package main

import (
	"fmt"
	"time"
)

/**
使用通道和go模拟工作池，类似线程池
 */
func main() {

	// 缓冲池为5的通道，用来放任务
	jobs := make(chan int, 5)
	result := make(chan string)

	worker := func(w int, js <-chan int, r chan<- string) {
		for j := range js {
			// 模拟业务操作
			time.Sleep(time.Millisecond * 1000)
			rs := fmt.Sprintf("worker-%d完成了job-%d", w, j)
			r <- rs
		}
	}

	go func() {
		i := 0
		for {
			i++
			jobs <- i
		}
	}()

	for i := 0; i < 2; i++ {
		go worker(i, jobs, result)
	}

	for r := range result {
		fmt.Println(r)
	}

}
