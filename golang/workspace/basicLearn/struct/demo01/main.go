package main

import "fmt"

type Student struct {
	Name string
	Age  int
}

func (s Student) sayHello() {
	fmt.Println("Hello", s.Name)
}

func (s *Student) print() {
	str := fmt.Sprintf("name=%v, age=%v", s.Name, s.Age)
	fmt.Println(str)
}

func (s *Student) setAge(age int) {
	s.Age = age
}

func main() {

	var s = Student{"ZhangSan", 30}
	s.sayHello()
	// 接收体为指针的方法也可以直接通过实例调用，这是因为go底层在编译的时候做了优化。同样接收体为实例的方法也是可以通过指针来调用的。
	s.print()
	var sp = &s
	sp.sayHello()
	sp.setAge(40)
	sp.print()

	(*sp).print()

}
