package main

import (
	"fmt"
	"sync"
)

var wg = sync.WaitGroup{}
// 正常情况下，主线程是不会等待go程的。通过WaitGroup可以实现让主线程等待go程结束后再继续执行
func main() {
	// 表明需要等待结束的go程数量加1个。内部有一个计数器，对应的计数器加1。
	wg.Add(1)
	go waitSecond()
	// 等待内部计数器的值变为0
	wg.Wait()
	fmt.Println("主线程结束")
}

func waitSecond() {
	fmt.Println("Enter WaitSecond......")
	defer fmt.Println("Out WaitSecond......")
	// 内部对应的计数器减1。
	wg.Done()
}
