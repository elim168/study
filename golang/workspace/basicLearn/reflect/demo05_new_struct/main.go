package main

import (
	"fmt"
	"reflect"
)

/*
	试验使用反射实例化结构体
*/

type Student struct {
	Name string
	Age  int
	Sex  string
}

func main() {

	// 定义Student指针类型的一个变量
	var stu *Student
	// 基于stu变量Student指针对应的reflect.Type类型的对象
	rtype := reflect.TypeOf(stu)
	// 是一个指针，*Student
	fmt.Println(rtype.Kind())
	// 取到实际底层的类型。即Student对应的reflect.Type
	rtype = rtype.Elem()
	// 输出的是Student
	fmt.Println(rtype)
	// reflect.New(reflect.Type)可以创建一个指定类型的实例，返回的是该实例的指针的reflect.Value对象。相当于*Student，因为rtype此时是Student
	var stuPointerValue reflect.Value = reflect.New(rtype)
	// 把通过反射构造的对象的指针赋值给stu变量
	stu = stuPointerValue.Interface().(*Student)
	stu.Age = 30
	// 也可以通过如下反射方式给字段赋值
	stuPointerValue.Elem().FieldByName("Name").SetString("张三")
	// 能输出Name和Age的值
	fmt.Println(stu)

}
