package main

import (
	"fmt"
	"os"
)

// 判断文件是否存在

func main() {

	fileInfo, err := os.Stat("/home/elim/.bashrc1111")
	if err == nil {
		// 	文件存在 false 2021-06-28 00:06:29.667727591 +0800 CST -rw-r--r-- .bashrc 4300
		fmt.Println("文件存在", fileInfo.IsDir(), fileInfo.ModTime(), fileInfo.Mode(), fileInfo.Name(), fileInfo.Size())
	} else if os.IsNotExist(err) {
		// fileInfo为nil
		fmt.Println("文件不存在", fileInfo)
	}

}
