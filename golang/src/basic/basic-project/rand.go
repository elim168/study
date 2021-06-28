package main

import (
	"fmt"
	"math/rand"
)

func main() {

	fmt.Println(rand.Int())
	fmt.Println(rand.Uint64())
	// Seed相同，每次第一次产生的随机数也相同
	rand.Seed(169)
	// 指定整数范围内的随机数，0-1000
	fmt.Println(rand.Intn(1000))
	// 0-1之间的随机数
	fmt.Println(rand.Float32())
	// 0-1之间的随机数，64位浮点数
	fmt.Println(rand.Float64())

	// 程序运行后连续产生的随机数，几乎每次都不一样。但是程序停止后重新运行一次后整体产生的随机数也上一次一致
	for i := 0; i < 20; i++ {
		fmt.Println(i, "随机数-", rand.Intn(1000))
	}


}
