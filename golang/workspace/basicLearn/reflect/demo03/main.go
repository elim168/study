package main

import (
	"fmt"
	"reflect"
)

// 运行test01会报错，改进方法请见test02。panic: reflect: reflect.Value.SetInt using unaddressable value
func test01() {
	num := 100
	rvalue := reflect.ValueOf(num)
	rvalue.SetInt(88)
	fmt.Println(num)
}

func test02() {
	num := 100
	// 由于Elem()对应的反射值的类型必须是interface{}或指针，所以下面改为取指针的反射
	rvalue := reflect.ValueOf(&num)
	// Elem returns the value that the interface v contains or that the pointer v points to. It panics if v's Kind is not Interface or Ptr. It returns the zero Value if v is nil.
	rvalue = rvalue.Elem()
	// 要改变值必须先通过Elem()方法获取底层值或者指针指向的那个值对应的reflect.Value对象。
	rvalue.SetInt(88)
	// 88
	fmt.Println(num)
}

func main() {
	// test01()
	test02()
}
