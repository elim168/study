package main

import (
	"bufio"
	"fmt"
	"log"
	"os"
)

// 演示文件的写入

func main() {

	// 只写方式打开文件，文件不存在时将创建文件，权限指定为0660
	file, err := os.OpenFile("/home/elim/golang.write.txt", os.O_WRONLY|os.O_CREATE, 0660)

	if err != nil {
		log.Fatal(err)
	}
	defer file.Close()

	// 带缓冲区的Writer
	writer := bufio.NewWriter(file)

	writer.WriteString("Hello Golang!\n")
	writer.WriteString("Hello World!\n")

	// 带缓冲区的Writer写完后一定要Flush一下文件内容才会真正的写入磁盘
	err = writer.Flush()
	if err != nil {
		log.Fatal(err)
	}

	fmt.Println("文件写入成功！")

}
