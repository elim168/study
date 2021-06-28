package main

import "fmt"

func main() {

	// 创建一个Map，它的Key是string，Value是int
	mymap := make(map[string]int)

	mymap["A"] = 1
	mymap["B"] = 2
	mymap["C"] = 3
	mymap["D"] = 4

	fmt.Println(mymap)

	// 指定map的初始化元素
	map2 := map[string]int{"A": 1, "B": 2}
	map3 := map[string]int{}
	map3["A"] = 1
	fmt.Println(map2, map3)
	// 遍历，单独遍历Key
	for key := range mymap {
		fmt.Println("Key=", key, ",Value=", mymap[key])
	}

	// 遍历，也可以同时Key/Value一起遍历
	for key, value := range mymap {
		fmt.Println("Key/Value一起遍历。Key=", key, ",Value=", value)
	}

	// 获取元素的值
	aValue := mymap["A"]
	fmt.Println("aValue", aValue)
	// 指定第二个参数可以用于判断指定的Key是否存在
	bValue,ok := mymap["B"]
	if ok {
		fmt.Println("Key B对应的值是：", bValue)
	} else {
		fmt.Println("Key B不存在")
	}
	// 指定的Key不存在时会返回该Key对应的0值。但最好不要通过此方式来判断Key是否存在，因为可能Key存在，而其中的值本来就是存储的0值。
	cvalue := mymap["DDDD"]
	fmt.Println("aaa", cvalue)

	// 删除某个Key
	delete(mymap, "A")
	fmt.Println("删除了Key后的map为：", mymap)

	// 定义一个map类型的变量m1,它的0值为nil
	var m1 map[string]int
	// nil值的map是不能为key赋值的。所以下面的语句会报错。
	//m1["A"] = 1
	fmt.Println(m1, m1 == nil)

}
