package main

import (
	"errors"
	"fmt"
)

// 单向的队列，里面的空间用完了就不能再重复使用了。

type Queue struct {
	maxSize int
	data    []int
	head    int // 头指针指向当前队列的下一个获取元素的位置
	tail    int // 尾指针，指向当前队列的下一个添加元素的位置

}

func (this *Queue) Push(value int) (err error) {
	if this.tail == this.maxSize {
		return errors.New("queue full, can not add any more")
	}
	this.data[this.tail] = value
	this.tail++
	return
}

func (this *Queue) Pop() (value int, err error) {
	if this.head == this.tail {
		return 0, errors.New("queue empty, can not get any more")
	}
	value = this.data[this.head]
	this.head++
	return value, nil
}

func (this *Queue) printValues() {
	for i := this.head; i < this.tail; i++ {
		fmt.Printf("data[%d]=%d \n", i, this.data[i])
	}
	fmt.Println(this.data)
}

func newQueue(size int) Queue {
	queue := Queue{maxSize: size}
	queue.data = make([]int, queue.maxSize)
	return queue
}

func main() {

	queue := newQueue(6)

	var key int
	for {
		fmt.Println("请选择：")
		fmt.Println("1. 添加元素")
		fmt.Println("2. 获取元素")
		fmt.Println("3. 查看所有元素")
		fmt.Println("4. 退出")

		fmt.Scanln(&key)
		switch key {
		case 1:
			fmt.Println("请输入需要添加的值：")
			var value int
			fmt.Scanln(&value)
			err := queue.Push(value)
			if err != nil {
				fmt.Println(err)
			}
		case 2:
			value, err := queue.Pop()
			if err != nil {
				fmt.Println(err)
			} else {
				fmt.Println("获取到的值为：", value)
			}
		case 3:
			queue.printValues()
		case 4:
			return
		default:
			fmt.Println("您的输入有误，请重新输入")
		}
	}

}
