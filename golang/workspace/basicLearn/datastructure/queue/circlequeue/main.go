package main

import (
	"errors"
	"fmt"
)

// 环形队列，相对于单向队列来说，它里面的数据是形成一个环的，弹出来的数据空闲出来的空间可以继续使用。

type Queue struct {
	maxSize int
	data    []int
	head    int  // 头指针指向当前队列的下一个获取元素的位置
	tail    int  // 尾指针，指向当前队列的下一个添加元素的位置
	cross   bool // 标记尾是否越界到头前面去了

}

func (this *Queue) Push(value int) (err error) {
	if this.IsFull() {
		return errors.New("queue full, can not add any more")
	}
	// 当前数据存放的位置
	pos := this.tail % this.maxSize
	this.data[pos] = value
	this.tail = pos + 1
	// 下一个位置越界了
	if this.tail == this.maxSize {
		this.cross = true
		this.tail = 0
	}
	return
}

func (this *Queue) Pop() (value int, err error) {
	if this.IsEmpty() {
		return 0, errors.New("queue empty, can not get any more")
	}
	// 当前取数据的位置
	pos := this.head % this.maxSize
	value = this.data[pos]
	this.head = pos + 1
	// 当头也越界的时候即为都未越界
	if this.head == this.maxSize {
		this.cross = false
		this.head = 0
	}
	return value, nil
}

func (this *Queue) IsEmpty() bool {
	return this.Size() == 0
}

func (this *Queue) IsFull() bool {
	return this.Size() == this.maxSize
}

func (this *Queue) Size() (size int) {
	if this.cross {
		size = this.maxSize - this.head + this.tail
	} else {
		size = this.tail - this.head
	}
	return size
}

func (this *Queue) PrintValues() {
	fmt.Printf("当前队列大小为：%d,head=%d,tail=%d,cross=%v\n", this.Size(), this.head, this.tail, this.cross)
	for i := 0; i < this.Size(); i++ {
		pos := (this.head + i) % this.maxSize
		fmt.Printf("data[%d]=%d \n", pos, this.data[pos])
	}
	fmt.Println(this.data)
}

func newQueue(size int) Queue {
	queue := Queue{maxSize: size}
	queue.data = make([]int, queue.maxSize)
	return queue
}

func main() {

	queue := newQueue(4)

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
			queue.PrintValues()
		case 4:
			return
		default:
			fmt.Println("您的输入有误，请重新输入")
		}
	}

}
