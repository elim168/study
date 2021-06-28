package main

import (
	"bufio"
	"fmt"
	"os"
	"strings"
)

func main() {

	// 从标准输入每次读取一行，读取后转换为大写并输出。按Ctrl+C退出程序
	scanner := bufio.NewScanner(os.Stdin)

	for scanner.Scan() {
		text := scanner.Text()
		upper := strings.ToUpper(text)
		fmt.Println(upper)
	}

	// Check for errors during `Scan`. End of file is
	// expected and not reported by `Scan` as an error.
	if err := scanner.Err(); err != nil {
		fmt.Fprintln(os.Stderr, "error:", err)
		os.Exit(1)
	}



}
