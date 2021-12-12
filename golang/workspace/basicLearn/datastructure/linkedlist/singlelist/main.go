package main

import "fmt"

/**
单向链表
*/

type LinkedList struct {
	head *Node // 头节点
	tail *Node // 尾节点
}

// 往链表中添加一个元素。默认从最后添加
func (this *LinkedList) Add(value interface{}) {
	var node = &Node{value: value}
	if this.head == nil {
		// 添加的第一个节点作为头节点
		this.head = node
		this.tail = node
		return
	}
	// 往原来的尾节点后面加
	this.tail.next = node
	// 新的节点就变成了原来的尾节点
	this.tail = node
}

// 删除一个元素
func (this *LinkedList) Remove(value interface{}) {
	removed := false
	var tempNode = &Node{}
	tempNode.next = this.head
	node := tempNode
	for node.next != nil {
		if node.next.value == value {
			// 当前节点的下一个节点是要删除的节点，则把当前节点的next指向next.next。
			node.next = node.next.next
			removed = true
			fmt.Println("删除了", value)
			break
		}
		node = node.next
	}
	if !removed {
		fmt.Println("要删除的节点不存在，value=", value)
	} else {
		// 删除的刚好是尾节点时要重新设置尾节点
		if node.next == nil {
			this.tail = node
		}
		// 有可能删除的是头节点，删除节点成功后要重新设置头节点
		this.head = tempNode.next
		// 头节点为空时，尾节点也要设置为空
		if this.head == nil {
			this.tail = nil
		}
	}

}

// 输出当前链表的值
func (this *LinkedList) Print() {
	fmt.Println("当前链表的值如下：")
	node := this.head
	if node == nil {
		fmt.Println("空链表")
		return
	}
	for node != nil {
		fmt.Println(node.value)
		node = node.next
	}
}

// 链表中的节点
type Node struct {
	value interface{} // 节点值
	next  *Node       // 下一个节点
}

func main() {
	var list = &LinkedList{}
	list.Print()
	for i := 0; i < 10; i++ {
		list.Add(i)
	}
	list.Print()
	for i := 0; i < 11; i++ {
		list.Remove((i + 5) % 11)
		list.Print()
	}

	list.Add(10)
	list.Add(11)
	list.Add(12)
	list.Remove(12)
	list.Add(13)
	list.Add(14)
	list.Add(15)
	list.Remove(15)
	list.Remove(14)
	list.Add(16)
	list.Print()

}
