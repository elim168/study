package main

import (
	"errors"
	"fmt"
)

// 链表实现的队列

type Node struct {
	value int   // 节点值
	next  *Node // 节点的下一个节点
}

type Queue struct {
	maxSize int   // 规定队列的最大值
	size    int   // 队列当前的大小
	head    *Node // 头节点
	tail    *Node // 尾节点
}

func (this *Queue) Push(value int) (err error) {
	if this.size == this.maxSize {
		return errors.New("queue full, can not add any more")
	}
	node := &Node{value: value}
	if this.tail != nil {
		this.tail.next = node
		this.tail = node
	} else {
		this.head = node
		this.tail = node
	}
	this.size++
	return
}

func (this *Queue) Pop() (value int, err error) {
	if this.head == nil {
		return 0, errors.New("queue empty, can not get any more")
	}
	value = this.head.value
	this.head = this.head.next
	// 刚好只有一个节点时，弹出去了后尾节点也要置空
	if this.head == nil {
		this.tail = nil
	}
	this.size--
	return value, nil
}

func (this *Queue) printValues() {
	fmt.Println("队列的值如下：")
	for node := this.head; node != nil; node = node.next {
		fmt.Println(node.value)
	}
}

func main() {

	queue := &Queue{maxSize: 5}

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
