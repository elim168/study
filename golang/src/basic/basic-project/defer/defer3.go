package main

import "fmt"

func main() {

	// defer是在程序return之前执行的，类似Java中的finally。它还可以对返回值进行修改。可以更改返回值中的变量的值
	fmt.Println(defer_func3(5))	// 15
	fmt.Println(defer_func3(50))	// 500

	// 方法内部定义返回值变量
	fmt.Println(defer_func3_2(5))	// 15
	fmt.Println(defer_func3_2(50))	// 60

}

// 指定了返回值对应的变量，然后改变这个变量的值是可以改变返回的值的。
func defer_func3(b int) (s int) {
	a := 10
	defer func() {
		if b > 10 {
			s = b * 10
		}
	}()
	s = a + b
	return
}

// 方法体内部指定了返回值变量。然后defer改变该变量的值返回的还是未更改的值。因为先执行return语句读到了result的值，此时方法还未结束，接着调用defer方法。
func defer_func3_2(b int) int {
	var result int
	a := 10
	defer func() {
		if b > 10 {
			result = b * 10
		}
	}()
	result = a + b
	return result
}