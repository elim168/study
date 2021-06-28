package main

import (
	"fmt"
	"os"
)

func main() {

	fileName := "Hello.txt"
	f, err := os.Create(fileName)
	if err != nil {
		panic(err)
	}
	fmt.Println("文件已创建，准备写入内容了")
	f.WriteString("Hello World!\n")
	fmt.Fprintln(f, "Hello Golang!")
	f.Close()


}
