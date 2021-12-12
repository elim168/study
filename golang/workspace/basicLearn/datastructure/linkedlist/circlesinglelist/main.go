package main

import "fmt"

/**
单向循环链表
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
		// 自己指向自己
		this.tail.next = this.head
		return
	}
	// 往原来的尾节点后面加
	this.tail.next = node
	// 新的节点就变成了原来的尾节点
	this.tail = node
	// 尾节点的下一个节点指向头节点
	this.tail.next = this.head
}

// 删除一个元素
func (this *LinkedList) Remove(value interface{}) {
	if this.head == nil {
		fmt.Println("空列表，删除的元素不存在")
		return
	}
	removed := false
	var tempNode = &Node{}
	tempNode.next = this.head
	this.tail.next = tempNode
	node := tempNode
	for node.next != tempNode {
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
		// 尾节点重新指向头节点
		this.tail.next = this.head
	} else {
		// 删除的刚好是尾节点时要重新设置尾节点
		if node.next == tempNode {
			this.tail = node
		}
		// 有可能删除的是头节点，删除节点成功后要重新设置头节点
		this.head = tempNode.next
		// 头节点为临时节点时，说明节点都删除了，链表为空了
		if this.head == tempNode {
			this.head = nil
			this.tail = nil
		} else {
			// 重新把尾指向头。原来的头可能已经删除了。
			this.tail.next = this.head
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
	for {
		fmt.Println(node.value)
		node = node.next
		// 遍历完一轮就退出了
		if node == this.head {
			break
		}
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
