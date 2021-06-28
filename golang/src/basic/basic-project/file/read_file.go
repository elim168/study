package main

import (
	"fmt"
	"io"
	"io/ioutil"
	"os"
)

func checkError(err error) {
	if err != nil {
		panic(err)
	}
}

func main() {

	// 读入一整个文件
	fileBytes, err := ioutil.ReadFile("/home/elim/.bashrc")
	checkError(err)
	fmt.Println("读取到的文件内容为：", string(fileBytes))

	file2, err2 := os.Open("/home/elim/.bashrc")
	checkError(err2)
	defer file2.Close()
	bytes := make([]byte, 200)
	// 读入内容到字节数组，实际读取到的长度为readLen
	readLen, err3 := file2.Read(bytes)
	checkError(err3)
	fmt.Println("读取到的长度为：", readLen, "字节，内容为：", string(bytes))
	// 循环读取文件
	for {
		readLen, err3 = file2.Read(bytes)
		if err3 == io.EOF {
			break
		}
		checkError(err3)
		if readLen != 200 {
			// 不足200字节的单独打印，因为后面部分还填充了上一次读取的内容
			b := make([]byte, readLen)
			copy(b, bytes)
			fmt.Println("最后一点内容：", string(b))
		} else {
			fmt.Println(string(bytes))
		}
	}


	//sets the offset for the next Read or Write on fileBytes to offset, interpreted
	// according to whence: 0 means relative to the origin of the fileBytes, 1 means
	// relative to the current offset, and 2 means relative to the end.
	// It returns the new offset and an error, if any.
	// 从整个文件的起始位置为10的位置开始读取。第2个参数传0表示从文件的起始位置开始计算offset。1是相对当前位置，2是从最末尾开始
	file2.Seek(10, 0)
	var b = make([]byte, 10)
	len, err4 := file2.Read(b)
	checkError(err4)
	fmt.Println("读取到的长度为：", len, "内容为：", string(b))


	b = make([]byte, 256)
	// 至少读100个字节到字节数组b，如果不到100个文件就结束了则会异常
	len, err = io.ReadAtLeast(file2, b, 100)
	fmt.Println("至少读取100个字节", len, string(b))

}
