package main

import (
	"fmt"
)

/**
 * 哈稀表，底层数据结构用数组，存取值时基于哈稀算法决定要数组的哪个位置获取元素。
 * 哈稀值是可能冲突的，所以数组中的元素需要利用一个链表把它存起来
 */

type Node struct {
	Key   interface{}
	Value interface{}
	Next  *Node
}

// 往节点的最后添加一个节点
func (this *Node) Push(node *Node) {
	last := this
	temp := this
	for temp != nil {
		last = temp
		temp = temp.Next
	}
	last.Next = node
}

// 在链表中基于Key找值
func (this *Node) SearchValue(key interface{}) interface{} {
	node := this.SearchNode(key)
	if node != nil {
		return node.Value
	}
	return nil
}

// 在链表中基于Key找值
func (this *Node) SearchNode(key interface{}) *Node {
	last := this
	for last != nil {
		if last.Key == key {
			return last
		}
		last = last.Next
	}
	return nil
}

// 在链表中删除指定节点
func (this *Node) Remove(key interface{}) *Node {
	dumyNode := &Node{Next: this}
	temp := dumyNode
	for temp.Next != nil {
		if temp.Next.Key == key {
			temp.Next = temp.Next.Next
			break
		}
		temp = temp.Next
	}
	return dumyNode.Next
}

// 输出链表的值
func (this *Node) Print() {
	last := this
	for last != nil {
		fmt.Printf("-->%v:%v", last.Key, last.Value)
		last = last.Next
	}
}

type HashTable struct {
	//  固定16个元素的数组。数组是值类型，默认会分配空间
	values [16]*Node
}

// 往哈稀表中添加一个键值对
func (this *HashTable) Put(key interface{}, value interface{}) {
	hashValue := this.hash(key)
	index := hashValue % len(this.values)
	headNode := this.values[index]
	if headNode == nil {
		this.values[index] = &Node{Key: key, Value: value}
	} else {
		// 原来的值已经存在了就更新值
		oldNode := headNode.SearchNode(key)
		if oldNode != nil {
			oldNode.Value = value
		} else {
			newNode := &Node{Key: key, Value: value}
			headNode.Push(newNode)
		}
	}
}

func (this *HashTable) Get(key interface{}) interface{} {
	hashValue := this.hash(key)
	index := hashValue % len(this.values)
	headNode := this.values[index]
	if headNode == nil {
		return nil
	} else {
		return headNode.SearchValue(key)
	}
}

func (this *HashTable) Remove(key interface{}) {
	hashValue := this.hash(key)
	index := hashValue % len(this.values)
	headNode := this.values[index]
	if headNode == nil {
		return
	}
	// 可能删除的节点刚好就是头节点，所以删除后需要重新指定头节点
	newHead := headNode.Remove(key)
	this.values[index] = newHead
}

// 获取Key对象的hash值
func (this *HashTable) hash(key interface{}) (hashValue int) {
	switch key.(type) {
	case string:
		keyValue := key.(string)
		for _, v := range keyValue {
			hashValue += int(v)
		}
	case int:
		hashValue = key.(int)
	case int32:
		hashValue = int(key.(int32))
	default:
		panic(fmt.Sprintf("Not Supported Key Type[%T]", key))
	}
	return
}

// 输出当前哈稀表的情况
func (this *HashTable) Print() {
	fmt.Println("当前哈稀表的值情况如下：")
	for i, value := range this.values {
		fmt.Printf("第%d个链表：", i)
		if value == nil {
			fmt.Print("空")
		} else {
			value.Print()
		}
		fmt.Println()
	}
}

func test1() {
	hashTable := &HashTable{}
	value := 'A'
	for i := 0; i < 60; i++ {
		cur := value + rune(i)
		hashTable.Put(cur, fmt.Sprintf("%c", cur))
	}
	hashTable.Print()
	fmt.Println(hashTable.Get(rune(75)), hashTable.Get(1000))
}

func test2() {
	hashTable := &HashTable{}
	hashTable.Put("A", 88)
	hashTable.Put("B", 99)
	hashTable.Put("C", 100)
	hashTable.Print()
	hashTable.Put("A", 77)
	hashTable.Put("Q", 66)
	hashTable.Put("R", 95)
	hashTable.Print()
	hashTable.Remove("A")
	hashTable.Remove("C")
	hashTable.Print()

}

func main() {
	// test1()
	test2()
}
