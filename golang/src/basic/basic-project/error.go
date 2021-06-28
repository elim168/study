package main

import (
	"errors"
	"fmt"
)


type CustomError struct {
	errorCode, errorMsg string
}

// 实现了error接口，重写其Error()方法
func (e *CustomError) Error() string {
	return fmt.Sprintf("errorCode=%s, errorMsg=%s", e.errorCode, e.errorMsg)
}


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
	}



	// 下面的内容使用自定义的异常
	f2 := func(num int) (int, error) {
		if num > 10 {
			// 使用自定义异常，需要返回异常对象的指针
			return -1, &CustomError{"E1001", "num不能大于10"}
		}
		return num * 100, nil
	}

	result2, e2 := f2(10)
	if e2 == nil {
		fmt.Println("call f2 success, result is", result2)
		// 错误对象的实例可以返回两个值，不为nil时第2个值为true，否则为false。第一个值是错误对象的实例。
		e2Obj, ok := e2.(*CustomError)
		fmt.Println(ok, e2Obj)
		// 第2个值接收时可以忽略，使用下面的方式即可
		//e2Obj = e2.(*CustomError)
	} else {
		fmt.Println("call f2 fail,", e2.Error())
		// 取具体的错误对象的实例需要这样取
		e2Obj, ok := e2.(*CustomError)
		fmt.Println(e2Obj.errorCode, e2Obj.errorMsg, ok)
	}

}
