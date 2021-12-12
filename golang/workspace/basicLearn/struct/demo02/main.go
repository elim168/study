package main

import (
	"fmt"

	"example.com/elim/basic/struct/demo02/model"
)

func main() {
	var student = model.NewStudent("张三", "男", 20)
	fmt.Println("姓名：", student.Name)
	// student结构体是非导出的，但是可以通过指针访问它对应的导出字段和方法
	fmt.Println("性别：", student.GetSex())
	fmt.Println("年龄：", student.Age)
}
