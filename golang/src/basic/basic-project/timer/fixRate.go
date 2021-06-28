package main

import (
	"fmt"
	"time"
)

func main() {

	// 按照固定的周期执行使用NewTicker。下面的程序会每秒打印一条记录

	ticker := time.NewTicker(time.Second)

	go func() {
		i := 0
		for t := range ticker.C {
			i++
			fmt.Println("ticker周期性的触发了一次，触发时间是：", t, i)
		}
	}()

	// ticker也跟timer一样，可以进行取消和重置时间
	//ticker.Stop()
	//ticker.Reset()
	// 周期任务还可以直接用time.Tick()，其底层会调用NewTicker()，并返回对应的通道C。
	tick := time.Tick(time.Second)
	for t := range tick {
		fmt.Println("周期任务触发了一次，", t)
	}

}
