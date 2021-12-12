package main

import "fmt"

type A struct {
	Name string
}

func (a A) sayHello() {
	fmt.Println("A sayHello", a.Name)
}

// 类型B组合了类型A，它将拥有A的字段和方法
type B struct {
	A
}

func main() {
	a := A{"ZhangSan"}
	a.sayHello()

	// 下面这种声明会报错，因为B中没有字段
	// b := B{"ZhangSan"}
	b := B{A{"Lisi"}}
	b.Name = "Lisi"
	b.sayHello()
}
