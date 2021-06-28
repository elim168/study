package main

import (
	"fmt"
	"os"
)

// 获取运行程序的命令行参数
func main() {

	// 获取命令行参数，对应的是一个数组，第一个参数是运行程序。
	args := os.Args

	fmt.Println("命令行参数：", args)

	for i, arg := range args {
		fmt.Printf("第%d个参数是%s\n", i, arg)
	}

	/**
	 go run command_line_arg.go 1 2 3 Hello "Hello World" "Hello Golang" 'Hello Elim'
	命令行参数： [/tmp/go-build3378194478/b001/exe/command_line_arg 1 2 3 Hello Hello World Hello Golang Hello Elim]
	第0个参数是/tmp/go-build3378194478/b001/exe/command_line_arg
	第1个参数是1
	第2个参数是2
	第3个参数是3
	第4个参数是Hello
	第5个参数是Hello World
	第6个参数是Hello Golang
	第7个参数是Hello Elim
	elim@elim-pc:~/dev/pro
	 */

}
