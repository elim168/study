package main

import (
	"bufio"
	"fmt"
	"os"
)

func main() {

	file, err := os.Open("/home/elim/.bashrc")
	if err != nil {
		panic(err)
	}
	defer file.Close()
	reader := bufio.NewReader(file)
	// 文档中不太建议用ReadLine。ReadLine is a low-level line-reading primitive. Most callers should use
	// ReadBytes('\n') or ReadString('\n') instead or use a Scanner.
	//如果一行太长了只返回了其中一部分时prefix为true，剩下的部分会在下次ReadLine()时返回
	line, prefix, err := reader.ReadLine()
	fmt.Println(string(line), prefix)

	// 一直读取，直到遇到分隔符\n，即换行符，返回遇到分隔符之前读取到的内容
	bytes, err := reader.ReadBytes('\n')
	fmt.Println("按照分隔符切分后的内容为：", string(bytes))

	b, err := reader.Peek(100)
	fmt.Println("接着读取100个字节，内容是：", string(b))

	file2, _ := os.Open("/home/elim/.profile")
	defer file2.Close()
	// 默认按行切分，一行一行读取
	scanner := bufio.NewScanner(file2)
	// 指定切分逻辑，默认是按换行符
	//scanner.Split()
	for scanner.Scan() {
		//fmt.Println("读取到的内容：", string(scanner.Bytes()))
		fmt.Println("读取到的内容：", scanner.Text())
	}

}
