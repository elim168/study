package main

import (
	"errors"
	"fmt"
)

// 栈

type Stack struct {
	MaxSize int   // 栈的最大值
	Top     int   // 栈顶的位置。Top==-1表示栈为空的
	Data    []int // 栈中的数据
}

func (this *Stack) IsFull() bool {
	return this.MaxSize-1 == this.Top
}

func (this *Stack) IsEmpty() bool {
	return this.Top == -1
}

// 推一个数到栈顶
func (this *Stack) Push(value int) (err error) {
	if this.IsFull() {
		return errors.New("Stack Full !")
	}
	this.Top++
	this.Data[this.Top] = value
	return
}

// 弹出一个数
func (this *Stack) Pop() (value int, err error) {
	if this.IsEmpty() {
		return -1, errors.New("Stack Empty !")
	}
	value = this.Data[this.Top]
	this.Top--
	return
}

// 展示栈中的所有数据
func (this *Stack) List() {
	if this.IsEmpty() {
		fmt.Println("Stack Empty !")
	}
	fmt.Println("Stack Values:")
	for i := this.Top; i >= 0; i-- {
		fmt.Printf("stack[%d] = %d \n", i, this.Data[i])
	}
}

func main() {
	stack := &Stack{Top: -1, MaxSize: 5, Data: make([]int, 5)}

	for i := 0; i < 6; i++ {
		err := stack.Push(i)
		if err != nil {
			fmt.Println("推数据到Stack失败。", err)
		}
	}

	stack.List()

	for i := 0; i < 6; i++ {
		value, err := stack.Pop()
		if err != nil {
			fmt.Println("弹出数据出错。", err)
		} else {
			fmt.Println("从Stack弹出了：", value)
		}
	}

	stack.List()

	stack.Push(100)
	stack.Push(200)

	stack.List()

}
