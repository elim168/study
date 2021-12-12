package main

import "fmt"

/*
常量定义
1. 常量是必须在声明的时候指定值的。
2. 定义时类型可以声明也可以不声明，不声明时将基于值进行断言
3. 常量声明的名称如果是小写的，则只能在包范围内访问，大写的才能在包范围外访问。
4. 它跟变量一样也可以在方法内部进行声明。
5. 结合iota使用
*/

const (
	num          = 100
	num2 float64 = 5.67
	Name         = "ZhangSan"
)

// 结合iota使用。第一行会赋值为0,之后每一行都在前一行的基础上加1。
const (
	a = iota
	b
	c
	d
)

const (
	a2     = iota
	b2     = iota
	c2, d2 = iota, iota
	e2     = iota
)

func main() {
	fmt.Println(num, num2, Name)
	// 0, 1, 2, 3
	fmt.Println(a, b, c, d)
	// 0, 1, 2, 2, 3。c2和d2都为2，e2为3,说明指定值为iota后，值是按行递增的。
	fmt.Println(a2, b2, c2, d2, e2)
}
