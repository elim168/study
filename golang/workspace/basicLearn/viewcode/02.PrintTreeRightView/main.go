package main

import "fmt"

// 输出二叉树的右视图
// 给定一颗二叉树，想象自己站在它的右侧，按照从顶部到底部的顺序，返回从右侧所能看到的节点值。
// 示例：
// 输入：[1,2,3,null,5,null,4]
// 输出：[1,3,4]
// 解释：
/**
		1
	 /     \
   2        3
    \          \
     5          4
*/

type TreeNode struct {
	Value int
	Left  *TreeNode
	Right *TreeNode
}

func buildTree(values []interface{}) *TreeNode {
	if len(values) == 0 {
		return nil
	}
	var root = &TreeNode{Value: values[0].(int)}
	var nodes []*TreeNode = []*TreeNode{root}
	// 数组元素当前的位置，两个一组
	var i = 1
	for len(nodes) > 0 && i < len(values) {
		curNode := nodes[0]
		leftValue := values[i]
		rightValue := values[i+1]
		if leftValue != nil {
			leftNode := &TreeNode{Value: leftValue.(int)}
			curNode.Left = leftNode
			nodes = append(nodes, leftNode)
		}
		if rightValue != nil {
			rightNode := &TreeNode{Value: rightValue.(int)}
			curNode.Right = rightNode
			nodes = append(nodes, rightNode)
		}
		// 跳过第一个已经遍历过的节点
		nodes = nodes[1:]
		i += 2
	}
	return root
}

func printTree(root *TreeNode) {
	fmt.Println("节点：", root.Value)
	if root.Left != nil {
		fmt.Println("左节点：", root.Left.Value)
		printTree(root.Left)
	} else {
		fmt.Println("左节点空")
	}
	if root.Right != nil {
		fmt.Println("右节点：", root.Right.Value)
		printTree(root.Right)
	} else {
		fmt.Println("右节点为空")
	}
}

func solution(root *TreeNode) []int {
	var res []int
	// 广度优先算法遍历树。同一层优先取最右边的节点

	// 存放同一层的节点
	var sameLevel []*TreeNode = []*TreeNode{root}
	for len(sameLevel) != 0 {
		mostRightNodeValue := sameLevel[len(sameLevel)-1].Value
		res = append(res, mostRightNodeValue)

		// 准备遍历下一层的节点
		var nextLevel []*TreeNode
		for _, node := range sameLevel {
			if node.Left != nil {
				nextLevel = append(nextLevel, node.Left)
			}
			if node.Right != nil {
				nextLevel = append(nextLevel, node.Right)
			}
		}
		sameLevel = nextLevel
	}

	return res
}

func printResult(values []interface{}) {
	root := buildTree(values)
	res := solution(root)
	fmt.Println("树形：", values, "右侧看到的结果：", res)
}

func main() {
	// var values []interface{} = []interface{}{1, 2, 3, nil, 5, nil, 4}
	// root := buildTree(values)
	// printTree(root)
	// fmt.Println(solution(root))
	printResult([]interface{}{1, 2, 3, nil, 5, nil, 4})
	printResult([]interface{}{1, 2, 3, nil, 5, nil, 4, 6, 7, nil, nil, 8, nil})
}
