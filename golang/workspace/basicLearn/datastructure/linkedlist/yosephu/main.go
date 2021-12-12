package main

import "fmt"

/*
环形链表处理约瑟夫问题。
有编号为1-n的n个小孩围成一圈，从编号为k（1 <=k<=n)的孩子开始数数，当数到m的时候，
数到的这个孩子出圈，然后从下一个孩子继续数到m，继续出圈，直到所有的孩子都出圈。
问出圈顺序是怎样的？
*/

type Child struct {
	No   int
	Next *Child
}

// 小孩子构成一个圈
func buildCircle(childNum int) (head *Child, tail *Child) {
	var curChild *Child
	for i := 0; i < childNum; i++ {
		curChild = &Child{No: i + 1}
		if i == 0 {
			head = curChild
		} else {
			tail.Next = curChild
		}
		tail = curChild
	}
	tail.Next = head
	return
}

// 输出小孩围成的圈子的情况
func PrintChild(head *Child) {
	fmt.Println("小孩子圈子如下：")
	curChild := head
	for {
		fmt.Printf("%d", curChild.No)
		curChild = curChild.Next
		if curChild == head {
			break
		} else {
			fmt.Print("->")
		}
	}
	fmt.Println()
}

// 开始玩游戏
// childNum: 小孩数量
// k：第一次从编号为k的小孩开始数
// m：每次数到m时当前的小孩出圈
func playGame(childNum, k, m int) {
	fmt.Printf("PlayGame(childNum=%d, k=%d, m=%d)\n", childNum, k, m)
	// 只有一个小孩直接出圈
	if childNum == 1 {
		fmt.Println("当前出圈的小孩是：", childNum)
		return
	}
	// 构造小孩围成一个圈。返回第一个小孩和第一个小孩前面一个小孩
	curChild, prevChild := buildCircle(childNum)
	// 输出看下当前的圈子的情况
	PrintChild(curChild)
	// 从k开始数，先定位到编号为k的孩子。需要往前走k-1步
	for i := 0; i < k-1; i++ {
		curChild = curChild.Next
		prevChild = prevChild.Next
	}

	// 开始数数了
	for curChild != prevChild {
		// 从1开始数到m，即从当前开始走m-1步。
		for i := 0; i < m-1; i++ {
			curChild = curChild.Next
			prevChild = prevChild.Next
		}

		// 当前定位到的小孩就是要出圈的
		fmt.Println("当前出圈的小孩是：", curChild.No)
		// 当前小孩往后移一个
		curChild = curChild.Next
		// 当前小孩的前一个小孩的下一个小孩指向新的当前小孩。这样原来的那个当前小孩的链接就断掉了，就出圈了。
		prevChild.Next = curChild

	}
	fmt.Println("当前出圈的小孩是：", curChild.No)
}

func main() {
	playGame(20, 8, 6)
}
