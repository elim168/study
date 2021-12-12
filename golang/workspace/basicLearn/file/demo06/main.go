package main

import (
	"fmt"
	"io/ioutil"
	"log"
)

// 文件拷贝

func main() {

	// 读取文件的内容。适合小文件
	data, err := ioutil.ReadFile("/home/elim/golang.write.txt")
	if err != nil {
		log.Fatal(err)
	}

	err = ioutil.WriteFile("/home/elim/golang.write.2.txt", data, 0660)
	if err != nil {
		log.Fatal(err)
	}

	fmt.Println("文件拷贝完成")

}
