package main

import (
	"crypto/md5"
	"fmt"
)

func main() {
	m := md5.New()
	// 写入需要转MD5的内容
	m.Write([]byte("Hello Golang"))
	// 触发转MD5操作。此时还可以顺带附加内容。附加的内容放最前面，附加的内容不会转MD5。
	//resultBytes := m.Sum([]byte("Hello"))
	// 如果不需要附加，则可以传入nil
	resultBytes := m.Sum(nil)
	fmt.Println(len(resultBytes))
	result := fmt.Sprintf("%x", resultBytes)
	fmt.Println(result, len(result))
}
