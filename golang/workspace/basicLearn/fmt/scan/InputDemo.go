package main

import "fmt"

func main() {
	var name string
	var age byte

	// 使用Scanln读取输入的变量

	fmt.Println("请输入姓名：")
	// 把输入的姓名赋值给name变量
	fmt.Scanln(&name)

	fmt.Println("请输入年龄：")
	fmt.Scanln(&age)

	fmt.Printf("name=%q, age=%d \n", name, age)

	// 方式二：使用Scanf读取输入的
	fmt.Println("请输入姓名和年龄，中间用空格隔开")
	fmt.Scanf("%s %d", &name, &age)
	fmt.Printf("方式二：name=%q, age=%d \n", name, age)
}
