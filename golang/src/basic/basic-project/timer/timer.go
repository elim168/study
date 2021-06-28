package main

import (
	"fmt"
	"time"
)

func main() {

	// 创建一个timer，该timer将在3秒后通知。通过它内部的C这个通道进行通知
	timer := time.NewTimer(time.Second * 1)
	// 时间未到前会阻塞在这里
	<-timer.C
	fmt.Println("timer的时间到了，即过了1秒了")

	// 如果只是想单纯的等待一个固定的时间，使用sleep也是可以的。相比之下timer的好处是它可以在时间未到前进行取消。
	timer2 := time.NewTimer(time.Second * 2)
	go func() {
		fmt.Println("timer2在等待中")
		<-timer2.C
		fmt.Println("timer2等待的时间已到")
	}()

	time.Sleep(time.Millisecond * 100)
	stopped := timer2.Stop()
	if stopped {
		fmt.Println("timer2已取消")
	}

	// timer的时间未到前可以重置超时时间
	t1 := time.Now()
	timer3 := time.NewTimer(time.Second * 2)
	timer3.Reset(time.Second * 3)
	value := <-timer3.C
	// 定时器到时间后发布的值是一个时间
	fmt.Println("定时器发布的值是：", value, value.Unix())
	t2 := time.Now()
	fmt.Println("time3超时时间达到了，耗时：", t2.Sub(t1))//3s

	// 定时器还可以简写为下面的形式。其内部就是调用了NewTimer()，然后返回了其通道C。
	after := time.After(time.Second * 3)
	t3 := <-after
	fmt.Println("等待的时间已经到了", t3)

	// AfterFunc会在指定的时间后执行对应的函数。其会返回内部的Timer对象，可以供后续进行重置或取消任务。
	timer4 := time.AfterFunc(time.Second, func() {
		fmt.Println("延时时间一秒到了，可以执行对应的函数开始工作了")
	})
	timer4.Reset(time.Second * 2)

}
