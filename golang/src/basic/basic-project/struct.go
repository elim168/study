package main

import (
	"fmt"
)

/**
定义了一个person类
 */
type person struct {
	name, sex string
	id, age int
}

// 类型之间互相引用
type father struct {
	name string
	son person
}


/*

P_Student
Golang中没有继承。下面的定义会让P_Student组合person类型的属性和方法，它可以直接访问person的属性和方法，但是调用方法时的属主都是person。因为它没有继承
 */
type P_Student struct {
	person
}

/**
构造方法
 */
func newPerson(id int, age int) person {
	return person{id: id, age: age}
}

/**
构造方法 自动生成的构造方法是返回指针的。
*/
func newPerson2(id int, age int) *person {
	return &person{id: id, age: age}
}

// Name get方法
func (p *person) Name() string {
	return p.name
}

// SetName Set方法
func (p *person) SetName(name string) {
	p.name = name
}

func (p *person) Sex() string {
	return p.sex
}

func (p *person) SetSex(sex string) {
	p.sex = sex
}

func (p *person) Id() int {
	return p.id
}

func (p *person) SetId(id int) {
	p.id = id
}

func (p *person) Age() int {
	return p.age
}

func (p *person) SetAge(age int) {
	p.age = age
}

// 给类型定义方法。如果方法需要改变对象的值，接收者需要定义为(p *person)。
func (p person) sayHello() {
	fmt.Println("Hello", p.name)
}

// MyInt 还可以基于现有类型定义新类型
type MyInt int



/**
结构体
 */
func main() {

	var p1 = person{name: "张三", sex: "男", id: 1, age: 10}
	p2 := person{name: "李四", id: 2}
	p3 := person{"王五", "女", 3, 1}
	// 什么属性也没有指定
	p4 := person{}

	fmt.Println(p1, p2, p3, p4)

	p3.SetAge(20)
	fmt.Println(p3, p3.Age(), p3.age)

	fmt.Println(newPerson(10, 50).age)

	fmt.Println(newPerson2(20, 30))//&{  20 30}
	// 对象的指针当作对象使用，会自动拆解，无需再加*
	fmt.Println(newPerson2(20, 30).id)

	persons := []person{p1, p2, p3}
	fmt.Println(persons)

	f1 := father{"张飞", p1}
	fmt.Println(f1, f1.name, f1.son.Name())

	// 也可以通过new关键字new(Type)来分配一个Type对应的对象的地址，此时它的值都是0值。返回的是对象的指针
	newP := new(person)
	newP.id = 1
	newP.name = "张三"
	fmt.Println(*newP, newP)


	// 下面定义了一个匿名的struct

	ps := []struct {
		id int
		name string
	} {
		{1, "ZhangSan"},
		{2, "Lisi"},
		{3, "wangwu"},
	}

	for i, p := range ps {
		fmt.Printf("序号：%d，Id：%d，姓名：%s\n", i, p.id, p.name)
	}

	var myint = MyInt(10)
	fmt.Println(myint, int(myint))

	// 构造时指定person的一些属性
	var s = P_Student{person{"李四", "男", 10, 30}}
	fmt.Println(s.id, s.name, s.Sex())

	var s2 = P_Student{}
	s2.name = "小七"
	fmt.Println(s2.id, s2.name, s2.Sex())

	// s作为person对象赋值给person类型的p1变量
	p1 = s.person
	fmt.Println(p1)

}
