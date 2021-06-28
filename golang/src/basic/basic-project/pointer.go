package main

import "fmt"

/**
指针
 */
func main() {

	a := 123
	// &a就是a 的指针，表示a的地址
	println(a, &a)

	// 把a的地址赋值给变量b，要取b的值需要用*b
	b := &a
	println(b, *b)
	// 对b重新赋值，它也会改变a的值，但是指针的地址是不会变的。
	*b = 3
	// 3 3 0xc000036768 0xc000036768
	println(*b, a, b, &a)

	fmt.Printf("打印变量a的指针：%v %p \n", &a, &a)

	// 值传递和引用传递。函数参数不是指针时对应的就是值传递，否则就是引用传递

	f1 := func(a1 int) {
		fmt.Println("a =", a1)
		a1 += 10
	}

	f1(a)
	fmt.Println("after call f1 a =", a)

	// 这里的函数参数是一个指针
	f2 := func(a1 *int) {
		fmt.Println("a =", *a1)
		*a1 += 10
	}

	f2(&a)
	fmt.Println("after call f2 a =", a)

}
