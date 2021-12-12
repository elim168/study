package main

import "fmt"

func func1(i int) (t int) {
	t = i
	defer func() {
		t += 3
	}()
	return t
}

func func2(i int) int {
	t := i
	defer func() {
		t += 3
	}()
	return t
}

func func3(i int) (t int) {
	defer func() {
		// 2。说明return后面的结果被赋值给了变量t。
		fmt.Println("current t=", t)
		t += i
	}()
	return 2
}

func main() {
	println(func1(1)) // 4
	println(func2(1)) // 1
	println(func3(1)) // 3
}
