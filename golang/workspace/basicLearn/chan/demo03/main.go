package main

import "fmt"

// 总数量
var num int = 1000

func write(data chan<- int) {
	for i := 1; i <= num; i++ {
		data <- i
	}
	close(data)
}

func calc(data <-chan int, resChan chan<- [2]int) {
	for v := range data {
		addRes := add(v)
		// 数字n和它的累加和的结果
		res := [2]int{v, addRes}
		resChan <- res
	}
}

func add(n int) int {
	res := 0
	for i := 1; i <= n; i++ {
		res += i
	}
	return res
}

func main() {
	dataChan := make(chan int, 200)
	resChan := make(chan [2]int, 50)
	go write(dataChan)
	for i := 0; i < 10; i++ {
		go calc(dataChan, resChan)
	}
	counter := 0
	for res := range resChan {
		fmt.Printf("从1累加到%v的结果是%v\n", res[0], res[1])
		counter++
		if counter == num {
			break
		}
	}
}
