package main

import (
	"fmt"
	"os"
	"strings"
)

// 环境变量的取值和设值
func main() {

	// 设置环境变量
	err := os.Setenv("myenv1", "Hello World!")
	if err != nil {
		return
	}

	envValue := os.Getenv("myenv1")
	fmt.Println("获取到的环境变量myenv1的值为", envValue)

	// Environ returns a copy of strings representing the environment, in the form "key=value".
	environ := os.Environ()
	for _,envPair := range  environ {
		pair := strings.Split(envPair, "=")
		fmt.Printf("环境变量%s=%s \n", pair[0], pair[1])
	}

}
