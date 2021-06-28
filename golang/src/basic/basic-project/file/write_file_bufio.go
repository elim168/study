package main

import (
	"bufio"
	"fmt"
	"os"
)

func main() {

	filePath := "/home/elim/golang_bufio_write.txt"
	os.Remove(filePath)

	file, err := os.Create(filePath)
	if err != nil {
		panic(err)
	}
	defer file.Close()

	writer := bufio.NewWriter(file)
	writer.Write([]byte("Hello\n"))
	writer.WriteString("你好，中国\n")
	fmt.Println(writer.Buffered(), writer.Available(), writer.Size())
	writer.Flush()


}
