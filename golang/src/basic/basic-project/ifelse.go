package main

import "fmt"

func main() {

	a := 100
	for a > 0 {
		a -= 5
		if a > 90 {
			fmt.Printf("a > 90, a=%d \n", a)
		} else if a > 80 {
			fmt.Printf("a > 80, a=%d \n", a)
		} else {
			fmt.Printf("a <= 80, a=%d \n", a)
			a -= 20
		}
	}

	// if 语句中还可以包含变量
	a = 100
	if b := a / 5; b > 10 {
		// 变量b可以在if语句中使用，但是不能在外部使用
		fmt.Printf("a/5 > 10, a=%d \n", b)
	} else {
		// 在else中也可以使用if语句中声明的变量b
		println("变量b的值为", b)
	}
	// 变量b的值在这里就访问不到了
	//println(b)

}
