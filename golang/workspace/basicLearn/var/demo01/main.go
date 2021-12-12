package main

import (
	"fmt"
	"unsafe"
)

var g1, g2, g3 = 1, 2, 3

func main() {
	var (
		b = 3
		c = 4
		e = 5
	)

	fmt.Println(b, c, e)

	var b1, c1, e1 = 10, 9, "Hello"
	fmt.Println(b1, c1, e1)

	var b2, c2, e2 int
	fmt.Println(b2, c2, e2)

	b3, c3, e3 := "Hello", "Golang", true
	fmt.Println(b3, c3, e3)

	fmt.Println(g1, g2, g3)
	// 可以查看一个变量占用的字节
	g1Size := unsafe.Sizeof(g1)
	fmt.Println("变量g1占用的字节大小是：", g1Size)

}
