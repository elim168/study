package main

import (
	"fmt"
	"reflect"
)

type Student struct {
	Name string
	Age  int
}

func main() {
	stu := Student{"ZhangSan", 30}
	rtype := reflect.TypeOf(stu)
	rvalue := reflect.ValueOf(stu)
	// 通过反射类型取Kind
	kind1 := rtype.Kind()
	// 通过反射value取Kind
	kind2 := rvalue.Kind()
	// 上述两者取出来的kind都是struct，实际类型是Student
	// kind1=struct,kind2=struct,type=main.Student
	fmt.Printf("kind1=%v,kind2=%v,type=%T \n", kind1, kind2, stu)

	// 类型断言
	stuName := rvalue.Interface().(Student).Name
	fmt.Println(stuName)

}
