package main

import "fmt"

// 定义的一个接口
type SayHi interface {
	hi(name string)
}

type SayHiImpl1 struct {}
type SayHiImpl2 struct {}

func (s SayHiImpl1) hi(name string) {
	fmt.Println("Hello SayHiImpl1.hi")
}

func (s SayHiImpl2) hi(name string) {
	fmt.Println("Hello SayHiImpl2.hi")
}

func (s SayHiImpl2) hi2(name string) {
	fmt.Println("Hello SayHiImpl2.hi2")
}

func main() {

	// 声明了变量s，类型为SayHi
	var s SayHi
	// 变量s赋值为SayHiImpl2类型
	s = SayHiImpl2{}

	// 调用其hi()
	s.hi("ZhangSan")
	// 把s变量强转为SayHiImpl1类型会报错，因为底层该变量是SayHiImpl2类型
	//s1 := s.(SayHiImpl1)
	//s1.hi("Lisi")
	// 把s变量强转为SayHiImpl2是可以的，因为它底层就是该类型。转成SayHiImpl2类型的对象后就可以调用对应的方法了。
	s2 := s.(SayHiImpl2)
	s2.hi("WangWu")
	s2.hi2("ZhaoLiu")


}
