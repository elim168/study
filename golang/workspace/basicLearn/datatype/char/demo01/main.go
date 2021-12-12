package main

import "fmt"

func main() {
	// char底层也是数字，小数字可以用byte接收，大的数字可以用int16或int接收
	var c1 byte = 'a'
	var c2 int = '中'

	// 输出为97 20013 ，对应的是数字
	fmt.Println(c1, c2)

	// 如果需要输出对应的字符的实际内容，可以使用格式化输出
	fmt.Printf("c1=%c,c2=%c \n", c1, c2)
	// c1=a,c2=中
}
