package main

import (
	"fmt"
	"reflect"
)

func main() {

	var a = 10
	// 通过反射获取变量的类型。TypeOf()返回的是reflect.Type类型
	fmt.Println(reflect.TypeOf(a), reflect.TypeOf(a).Name(), reflect.TypeOf(a).String())//int int int
	// 通过反射获取变量的值。ValueOf()返回的是reflect.Value类型的值
	fmt.Println(reflect.ValueOf(a), reflect.ValueOf(a).Int())//10 10

	// Kind()返回条目的类型。
	fmt.Println(reflect.ValueOf(a).Kind() == reflect.Int64)


}
