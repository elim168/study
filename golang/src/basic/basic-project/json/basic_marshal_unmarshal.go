package main

import (
	"encoding/json"
	"fmt"
	"os"
)

type Person struct {
	Id int
	Name string
	age int
}

type Person2 struct {
	// 指定该属性转换为json时key为id，而不是默认的Id
	Id int `json:"id"`
	Name string `json:"name"`
	age int
}

func main() {

	// 基本数据类型转换为json。转换后的对象为[]byte
	v1, err := json.Marshal(1)
	if err != nil {
		panic(err)
	}
	fmt.Println(string(v1)) // 1
	v2, _ := json.Marshal(2.56)
	fmt.Println(string(v2))

	v3, _ := json.Marshal(2.56)
	fmt.Println(string(v3))

	a1 := []int{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}
	v4, _ := json.Marshal(a1)
	fmt.Println(string(v4))

	m1 := map[string]string{"A": "a", "B": "b", "C": "c"}
	v5,_ := json.Marshal(m1)
	fmt.Println(string(v5))

	// 自定义对象只会把对外发布的，即首字母大写的属性转为JSON。即age是不会被转换的。
	p1 := Person{1, "ZhangSan", 35}
	v6,_ := json.Marshal(p1)
	fmt.Println(string(v6))//{"Id":1,"Name":"ZhangSan"}

	// 自定义对象转换为JSON字符串时还可以指定属性转换后的key的名称
	p2 := Person2{1, "ZhangSan", 35}
	v7,_ := json.Marshal(p2)
	fmt.Println(string(v7))//{"id":1,"name":"ZhangSan"}

	// 使用对象的指针也是可以转成JSON字符串的
	p3 := &Person2{2, "LiSi", 50}
	v8,_ := json.Marshal(p3)
	fmt.Println(string(v8))

	// 转换后的JSON字符串也可以输出到其它Writer，比如stdout。对应的Decoder也可以来自其它输入，比如stdin。
	encoder := json.NewEncoder(os.Stdout)
	encoder.Encode(p3)

	/**
	JSON字符串转换为对象
	 */
	// 定义一个map类型，用来接收JSON字符串转换后的对象
	var m2 map[string]interface{}
	// Unmarshal的入参需要是，第一个入参为JSON字符串的字节数组表示，第二个参数为接收转换后的对象的map的指针
	err = json.Unmarshal(v8, &m2)
	if err != nil {
		panic(err)
	}
	fmt.Println("转换后的对象为：", m2, m2["id"], m2["name"], m2["id"].(float64))

	// 也可以直接把JSON字符串转换为我们的自定义对象。定义一个Person2的空对象
	p4 := Person2{}
	if err := json.Unmarshal(v8, &p4); err != nil {
		panic(err)
	}
	fmt.Println("直接转换为Person2对象后的结果为：", p4)

}
