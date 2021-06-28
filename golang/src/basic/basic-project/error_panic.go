package main

import (
	"errors"
	"fmt"
)



func main() {

	// golang中的异常内置的是error类型。它需要作为返回值进行返回。调用完成后需要判断error对象是否为空。
	f1 := func (num int) (int, error) {
		if num > 10 {
			// new一个基本的异常，直接提供异常信息
			return -1, errors.New("num should not be greater than 10")
		}
		return num * 100, nil
	}

	result, e := f1(100)
	if e == nil {
		fmt.Println("call f1 success, result is ", result)
	} else {
		fmt.Println("call f1 fail!", e.Error())
		//panic("遇到了一个错误，给出错误信息描述")
		// 相当于Java中的抛异常，它会中断程序
		panic(e)
	}


}
