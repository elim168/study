package main

import (
	"fmt"
	"time"
)

// 同时定义多个变量
var (
	// 不指定变量的类型时将由后面的赋值的类型来推导它的类型
	a = 1
	b = "ABC"
	c = 1.8
	d = true
	// 定义变量的时候还可以直接指定类型，下面就指定了变量e的类型为int，值为5
	e int = 5
)

// func之外的变量只能通过var定义
var f = 'F'

// STATUS 定义常量
const STATUS = 1
const Status0 string = "1"


func main() {

	fmt.Println(a, b, c, d, e, f)
	fmt.Println(STATUS, Status0)

	// 方法内部也可以通过var来定义变量
	var g = 1
	// 方法内部也可以定义常量
	const HH = "HHHString"

	// 不使用var定义变量时，也可以使用:=来定义，后面需要跟变量的初始化值。变量类型由初始值来推导
	i := 5

	/**
	:=和var的区别是前者只能用于方法体内，而后者是内外都可以。
	前者在定义变量时必须指定变量的值，且不能指定类型，类型由变量值推导出来。而后者可以不指定变量的值，也可以指定变量的类型。
	 */
	j := 10

	var (
		H = 1
		I = 2
		J = 3
		K = 4
		L = 5
	)

	println(g, HH, i, j)
	println(H, I, J, K, L)

	println(time.Now().Date())
	println(fmt.Sprint(time.Now()))
	println(time.Now().Month())

	println(time.Now().String())

}
