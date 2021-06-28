package main

import (
	"encoding/base64"
	"fmt"
)

func main() {

	/**
	base64支持标准的base64和兼容URLEncoding的base64。它俩的区别如下。标准的有/，兼容URL的把/替换为_：
	const encodeStd = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
	const encodeURL = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_"
	 */
	// 把字符串使用标准的BASE64编码为字符串
	base64Str := base64.StdEncoding.EncodeToString([]byte("Hello Golang"))
	fmt.Println("生成的base64字符串为：", base64Str)
	// 字符串base64解码后是字节数组
	decodeBytes, err := base64.StdEncoding.DecodeString(base64Str)
	if err != nil {
		panic(err)
	}
	fmt.Println("解码后的内容为：", string(decodeBytes))

	var srcBytes = []byte("Hello Golang")
	var destBytes = make([]byte, len(srcBytes) * 4)
	// 源数据和结果数据都是字节数组
	base64.StdEncoding.Encode(destBytes, srcBytes)
	fmt.Println("编码后的内容为：", string(destBytes))

	// 兼容URL的base64
	result := base64.URLEncoding.EncodeToString([]byte("Hello Golang"))
	fmt.Println(result)

}
