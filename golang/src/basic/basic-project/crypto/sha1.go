package main

import (
	"crypto/sha1"
	"fmt"
)

func main() {

	// 新建一个sha1对象
	s := sha1.New()
	// 写入需哟转换为sha1的内容，字节数组表示
	len, err := s.Write([]byte("Hello Golang"))
	if err != nil {
		panic(err)
	} else {
		fmt.Println("len为", len)
	}

	// 获取转换为sha1的字节数组，获取时还可以再附加一些内容。附加的内容会放最前面，附加的内容不会转sha1。
	//resultBytes := s.Sum([]byte("Hello World"))
	// 不需要附加内容可以传递一个nil
	resultBytes := s.Sum(nil)
	// sha1对应的16进制字符串
	fmt.Printf("%x\n", resultBytes)
	result := fmt.Sprintf("%x", resultBytes)
	fmt.Println(result)

}
