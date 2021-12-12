package main

import "fmt"

type Student struct {
	id, age int
	name string
}

func main() {

	// 下面第一种方式每次都是取的变量s的指针，而变量s的指针是固定的，最终都指向了最后一个元素
	// 而第二种方式，每次都是取的分片中具体值的指针，每次都是不一样的。所以可以达到我们想要的输出效果。

	/**
	第一种方式的Value值都是一样的
	Key=Lisi,Value=&{3 50 WangWu}
	Key=WangWu,Value=&{3 50 WangWu}
	Key=ZhangSan,Value=&{3 50 WangWu}

	第二种方式可以达到想要的效果，Value值各不相同。
	Key=WangWu,Value=&{3 32 WangWu}
	Key=ZhangSan,Value=&{1 50 ZhangSan}
	Key=Lisi,Value=&{2 31 Lisi}
	*/
	map2_1()
	map2_2()

}

func map2_1() {
	var map1 = make(map[string]*Student)

	students := []Student {
		{1, 30, "ZhangSan"},
		{2, 31, "Lisi"},
		{3, 32, "WangWu"},
	}
	for _, s := range students {
		// 变量都是同一个s，所以里面的值都是一样的，最终都是最后一个对象的值
		map1[s.name] = &s
	}

	map1["ZhangSan"].age = 50

	/**
	输出如下：
	Key=ZhangSan,Value=&{3 50 WangWu}
	Key=Lisi,Value=&{3 50 WangWu}
	Key=WangWu,Value=&{3 50 WangWu}
	*/
	for k,v := range map1 {
		fmt.Printf("Key=%s,Value=%v \n", k, v)
	}
}

func map2_2() {
	var map1 = make(map[string]*Student)

	students := []Student {
		{1, 30, "ZhangSan"},
		{2, 31, "Lisi"},
		{3, 32, "WangWu"},
	}
	for i, s := range students {
		// 这里都是取的实际值的指针。所以值会是不一样的。
		map1[s.name] = &students[i]
	}

	map1["ZhangSan"].age = 50

	/**
	输出如下：
	Key=ZhangSan,Value=&{3 50 WangWu}
	Key=Lisi,Value=&{3 50 WangWu}
	Key=WangWu,Value=&{3 50 WangWu}
	*/
	for k,v := range map1 {
		fmt.Printf("Key=%s,Value=%v \n", k, v)
	}
}

