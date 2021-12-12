package main

import "fmt"

/**
双向链表
*/

func newLinkedList() *LinkedList {
	// 头节点
	var head = &Node{}
	// 一开始头节点和尾节点都指向同一个节点
	return &LinkedList{head, head}
}

type LinkedList struct {
	head *Node // 头节点
	tail *Node // 尾节点
}

// 往链表中添加一个元素。默认从最后添加
func (this *LinkedList) Add(value interface{}) {
	var node = &Node{value: value}
	// 往原来的尾节点后面加
	this.tail.next = node
	// 新节点的前一个节点等于原来的尾节点
	node.prev = this.tail
	// 新的节点就变成了原来的尾节点
	this.tail = node
}

// 删除一个元素
func (this *LinkedList) Remove(value interface{}) {
	removed := false
	// 头节点的下一个节点作为第一个节点
	node := this.head.next
	for node != nil {
		if node.value == value {
			// 1. 当前节点的前一个节点的下一个节点指向当前节点的下一个节点。
			node.prev.next = node.next
			// 2. 如果当前节点的下一个节点不为空，则把下一个节点的前节点指向当前节点的前节点
			if node.next != nil {
				node.next.prev = node.prev
			} else {
				// 当前节点的下一个节点为空，说明当前节点是尾节点，此时要更新tail节点
				this.tail = node.prev
			}
			// 经过上面的两步后当前节点就删除了。
			removed = true
			fmt.Println("删除了", value)
			break
		}
		node = node.next
	}
	if !removed {
		fmt.Println("要删除的节点不存在，value=", value)
	}

}

// 输出当前链表的值
func (this *LinkedList) Print() {
	fmt.Println("当前链表的值如下：")
	node := this.head.next
	if node == nil {
		fmt.Println("空链表")
		return
	}
	for node != nil {
		fmt.Println(node.value)
		node = node.next
	}
}

// 逆向的输出当前链表的值
func (this *LinkedList) PrintReverse() {
	fmt.Println("当前链表的值逆向输出如下：")
	node := this.tail
	if node == this.head {
		fmt.Println("空链表")
		return
	}
	for node != this.head {
		fmt.Println(node.value)
		node = node.prev
	}
}

// 链表中的节点
type Node struct {
	value interface{} // 节点值
	next  *Node       // 下一个节点
	prev  *Node       // 前一个节点
}

func main() {
	var list = newLinkedList()
	list.Print()
	list.PrintReverse()
	for i := 0; i < 10; i++ {
		list.Add(i)
	}
	list.Print()
	list.PrintReverse()

	list.Remove(5)
	list.Print()
	list.PrintReverse()

	list.Remove(9)
	list.Print()
	list.PrintReverse()
}
