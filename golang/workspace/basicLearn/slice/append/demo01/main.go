package main

import "fmt"

func main() {
	a := []int{1}
	fmt.Printf("变量a的对应的切片的地址是：%p，变量a自身的地址是：%p,值是：%v \n", a, &a, a)
	fmt.Printf("切片底层的数组的首地址是：%p，值是：%v \n", &a[0], a[0])
	a = append(a, 2, 3)

	// 因为切片是引用类型，一开始变量a存储的值就是切片的地址，所以append重新赋值后只是变量a存储的值变为新切片的地址了，
	// 但是变量a自身的地址是没有变化的
	fmt.Printf("变量a的对应的切片的地址是：%p，变量a自身的地址是：%p,值是：%v \n", a, &a, a)
	// 底层数组的首地址是不一样的，说明底层的数组的地址是变化了的
	fmt.Printf("切片底层的数组的首地址是：%p，值是：%v \n", &a[0], a[0])

}