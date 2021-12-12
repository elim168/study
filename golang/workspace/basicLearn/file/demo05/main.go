package main

import (
	"bufio"
	"fmt"
	"io"
	"log"
	"os"
)

// 对文件读和写

func main() {
	// 读写方式和追加方式打开文件，权限指定为0660
	file, err := os.OpenFile("/home/elim/golang.write.txt", os.O_RDWR|os.O_APPEND, 0660)
	if err != nil {
		log.Fatal(err)
	}
	defer file.Close()

	reader := bufio.NewReader(file)
	for {
		line, err := reader.ReadString('\n')
		if err == io.EOF {
			fmt.Println("文件读取完毕")
			break
		}
		// 输出读取到的行，不需要额外输出换行符
		fmt.Print(line)
	}

	writer := bufio.NewWriter(file)

	writer.WriteString("你好，中国!\n")
	writer.WriteString("你好，世界!\n")
	writer.Flush()

}
