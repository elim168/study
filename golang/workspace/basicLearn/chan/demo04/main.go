package main

import (
	"fmt"
	"time"
)

/*
	统计10000以内的所有的素数。
	为了提升并行能力，开启多个协程
*/

// 统计n以内的素数，把从1-n的数字放到管道numChan中。
func readyNum(n int, numChan chan<- int) {
	for i := 1; i <= n; i++ {
		numChan <- i
	}
	// 关闭numChan，好让其它读取numChan的协程知道数据已经读取完毕了。
	close(numChan)
}

func calcPrime(numChan <-chan int, primeChan chan<- int, calcDoneChan chan<- int) {
	for n := range numChan {
		if isPrime(n) {
			primeChan <- n
		}
	}
	// numChan遍历完成后，则表示当前计算素数的协程已经工作完成了。可以往calcDoneChan放一条数据，表示已经完成了
	calcDoneChan <- 1
}

// 校验计算素数的任务是否都完成了。calcTaskNum: 计算素数的任务数量。任务都完成了则关闭primeChan。
func checkCalcPrimeDone(calcTaskNum int, calcDoneChan <-chan int, primeChan chan int) {
	for i := 0; i < calcTaskNum; i++ {
		// 读一条数据并丢弃，主要用于同步
		<-calcDoneChan
	}
	// 所有工作都做完了后则关闭primeChan。主线程在遍历primeChan时则可以结束
	close(primeChan)
}

// 校验一个数字是否是素数，是则返回true
func isPrime(n int) (res bool) {
	res = true
	for i := 2; i < n; i++ {
		if n%i == 0 {
			res = false
			break
		}
	}
	return
}

func main() {

	t1 := time.Now().UnixNano()

	// 多少以内的素数
	n := 200000
	// 计算素数的协程数量
	calcTaskNum := 10

	// 存放待计算素数的通道
	numChan := make(chan int, 5000)
	// 存放素数的通道
	primeChan := make(chan int, 5000)
	// 存放统计协程是否完成的通道
	calcDoneChan := make(chan int, calcTaskNum)

	// 一个协程负责准备数据
	go readyNum(n, numChan)

	// 开启计算素数的协程
	for i := 0; i < calcTaskNum; i++ {
		go calcPrime(numChan, primeChan, calcDoneChan)
	}

	// 开启监听计算素数完成任务的协程
	go checkCalcPrimeDone(calcTaskNum, calcDoneChan, primeChan)

	// 存放所有的素数
	res := make([]int, 0)
	for prime := range primeChan {
		res = append(res, prime)
	}

	t2 := time.Now().UnixNano()
	fmt.Println("所有的素数为：", res)

	fmt.Printf("计算%v以内的素数耗时%vms\n", n, (t2-t1)/1e6)

}
