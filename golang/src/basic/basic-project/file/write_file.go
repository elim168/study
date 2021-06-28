package main

import (
	"io/ioutil"
	"os"
)

func checkErr(err error) {
	if err != nil {
		panic(err)
	}
}

func main() {

	os.Remove("/home/elim/golang_write.txt")
	os.Remove("/home/elim/golang_write2.txt")

	// 写入一个文件，文件不存在会创建文件，权限是777
	err := ioutil.WriteFile("/home/elim/golang_write.txt", []byte("Hello Golang"), 0644)
	checkErr(err)

	// 创建一个文件
	file, err := os.Create("/home/elim/golang_write2.txt")
	defer file.Close()
	// 直接写入字节数组
	file.Write([]byte{128, 96, 85, 220})
	file.Write([]byte("Hello\n"))
	// 从指定位置开始写
	file.WriteAt([]byte("你好"), 8)
	// 直接写入字符串
	file.WriteString("中国")

}
