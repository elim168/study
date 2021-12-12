package main

import (
	"fmt"
	"io/ioutil"
	"log"
)

func main() {
	// 一次性读取文件内容。适合小文件
	bytes, err := ioutil.ReadFile("/home/elim/.bashrc")

	if err != nil {
		// Fatal在输出异常后会退出程序
		log.Fatal(err)
	}

	fmt.Println("读取的文件内容为：", string(bytes))

}
