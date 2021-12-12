package main

import (
	"encoding/json"
	"fmt"
)

type Node struct {
	Row   int
	Col   int
	Value int
}

func main() {

	// 五子棋，一个11行11列的棋盘，白棋用1表示，黑棋用2表示，空用0表示
	var chessMap [11][11]int
	chessMap[5][6] = 1
	chessMap[3][2] = 2
	// 输出原数组
	for _, row := range chessMap {
		fmt.Println(row)
	}

	// 把上面的数组的内容替换为稀疏数组
	var sparseArray []Node
	// 原数组的行和列，以及默认值
	node := Node{11, 11, 0}
	sparseArray = append(sparseArray, node)
	for i, row := range chessMap {
		for j, value := range row {
			if value != 0 {
				// 非默认值就记录下来
				node = Node{i, j, value}
				sparseArray = append(sparseArray, node)
			}
		}
	}

	// 输出稀疏数组的内容
	fmt.Println("生成的稀疏数组的内容为：")
	fmt.Println(sparseArray)

	// 恢复稀疏数组
	sparseArrayInfoNode := sparseArray[0]
	rowNum := sparseArrayInfoNode.Row
	colNum := sparseArrayInfoNode.Col

	var chessMap2 = make([][]int, rowNum)
	for i := 0; i < rowNum; i++ {
		chessMap2[i] = make([]int, colNum)
	}
	for i, node := range sparseArray {
		if i == 0 {
			continue
		}
		chessMap2[node.Row][node.Col] = node.Value
	}

	// 输出还原数组
	for _, row := range chessMap2 {
		fmt.Println(row)
	}
	data, err := json.Marshal(sparseArray)
	if err != nil {
		fmt.Println("marshal err", err)
		return
	}
	fmt.Println("转换为JSON后的结果为：", string(data))

	// length默认为0
	var sparseArray2 []Node
	fmt.Println(sparseArray2)
	json.Unmarshal(data, &sparseArray2)

	fmt.Println("基于JSON反序列化的对象为：", sparseArray2)

}
