package main

import (
	"fmt"
	"reflect"
)

func main() {

	num := 10

	// 反射中的TypeOf()可以获取一个值对应的类型相关的信息。
	rtype := reflect.TypeOf(num)
	// rtype=int,实际类型为*reflect.rtype
	fmt.Printf("rtype=%v,实际类型为%T \n", rtype, rtype)
	fmt.Println(rtype.Bits())
	fmt.Println(rtype.Kind())
	fmt.Println(rtype.Name())

	// 反射的ValueOf可以获取对应的值相关的信息
	rvalue := reflect.ValueOf(num)
	// rvalue=10,实际类型为reflect.Value
	fmt.Printf("rvalue=%v,实际类型为%T \n", rvalue, rvalue)
	// 获取底层值的实际的int值。如果相关的kind不是int8/int16/int32/int64/int则会抛出异常
	fmt.Println(rvalue.Int())

	// rvalue.Interface()用于获取值转换为空接口的值，之后可以通过类型转换转成想要的类型
	fmt.Println(rvalue.Interface().(int))

}
