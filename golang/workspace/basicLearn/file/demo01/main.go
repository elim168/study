package main

import (
	"fmt"
	"os"
)

func main() {
	file, err := os.Open("/home/elim/.bashrc1")
	if err != nil {
		// 给定一个不存在的文件时。读取文件失败，err= open /home/elim/.bashrc1: no such file or directory
		fmt.Println("读取文件失败，err=", err)
		// 文件不存在时下面返回false。
		fmt.Println(err == os.ErrNotExist)
		// 文件不存在时下面返回true
		fmt.Println(os.IsNotExist(err))
	} else {
		// 返回的文件是一个指针。读取文件成功，file= &{0xc000056180}。
		fmt.Println("读取文件成功，file=", file)
		err = file.Close()
		if err != nil {
			fmt.Println("关闭文件失败")
		} else {
			fmt.Println("关闭文件成功")
		}
	}
}
