package main

import (
	"encoding/json"
	"fmt"
	"log"
)

type Student struct {
	Id int
	// 序列化为JSON时还可以通过打tag的方式指定序列化后的Key的名字
	Name  string `json:"name"`
	Age   int    `json:"age"`
	IsBoy bool
	// 序列化为JSON时只会序列化可导出字段。所以sex不会被序列化
	//sex int
}

func testStruct() {
	var stu = Student{
		Id:    1,
		Name:  "ZhangSan",
		Age:   18,
		IsBoy: true,
	}

	jsonBytes, err := json.Marshal(stu)
	if err != nil {
		log.Fatal("Student对象转换为JSON字符串出错", err)
	}
	jsonStr := string(jsonBytes)
	fmt.Println("stu转换为JSON字符串的结果是：", string(jsonStr))
}

func testMap() {
	var stuMap = make(map[int]Student)
	var stus = []Student{{1, "张三", 18, true}, {2, "李四", 19, false}}
	for _, stu := range stus {
		stuMap[stu.Id] = stu
	}
	jsonBytes, err := json.Marshal(stuMap)
	if err != nil {
		log.Fatal(err)
	}
	jsonStr := string(jsonBytes)
	fmt.Println("map转换后的JSON字符串：", jsonStr)
}

func testSlice() {
	var stus = []Student{{1, "张三", 18, true}, {2, "李四", 19, false}}
	jsonBytes, err := json.Marshal(stus)
	if err != nil {
		log.Fatal(err)
	}
	jsonStr := string(jsonBytes)
	fmt.Println("Slice转换为JSON字符串：", jsonStr)
}

func main() {
	testStruct()
	testMap()
	testSlice()
}
