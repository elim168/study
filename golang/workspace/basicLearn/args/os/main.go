package main

import (
	"fmt"
	"os"
)

func main() {
	// os.Args可以取到程序执行时指定的命令行参数。第一个参数是程序自身
	args := os.Args
	for i, arg := range args {
		fmt.Printf("第[%v]个参数是[%v] \n", i, arg)
	}

	/*

		正常是需要go build生成可执行文件再执行的。

		$ go run args/os/main.go abc.abc.a 123 ddd aaa
		第[0]个参数是[/tmp/go-build3107679385/b001/exe/main]
		第[1]个参数是[abc.abc.a]
		第[2]个参数是[123]
		第[3]个参数是[ddd]
		第[4]个参数是[aaa]
	*/
}
