package main

import (
	"fmt"
)

// 标准函数定义，有入参，有返回值类型
func add(i, j int) int {
	return i + j
}

// 函数可以返回多个值
func swap(i, j int) (int, int) {
	return j, i
}

func reverse(h string, i, j, k, l, m, n int) (int, int, int, int, int, int, string) {
	return n, m, l, k, j, i, h
}

// 匿名函数。函数的返回值是一个函数。可以基于它来实现闭包
func initSeq() func () int {
	i := 0
	return func() int {
		i += 1
		return i
	}
}

// add2 方法的返回参数也可以进行命名，如下面定义的sum。指定了返回参数的变量名后，
// 在函数的return后可以不加返回值，其将自动返回函数声明上返回值对应变量的当前值。
// 当然也是可以进行显式的指定的。
func add2(a, b int) (sum int) {
	sum = a + b
	return
}

func main() {

	fmt.Println(add(10, 2))
	i, j := swap(10, 20)
	fmt.Println(i, j)

	n, m, l, k, j, i, h := reverse("Hello", 1, 2, 3, 4, 5, 6)
	fmt.Println(n, m, l, k, j, i, h)


	// 可以定义内部函数
	innerFunc := func(i, j int) int {
		return i + j
	}

	a := innerFunc(10, 20)
	fmt.Println(a)

	nextInt := initSeq()
	// 输出1-10。内部的i一直在递增
	for o := 0; o < 10; o++ {
		fmt.Println(nextInt())
	}

	// 重新来一个实例又是从1开始的
	fmt.Println(initSeq()())

	fmt.Println(add2(10, 20))

}
