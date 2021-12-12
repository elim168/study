package main

import (
	"bufio"
	"fmt"
	"io"
	"os"
)

func main() {

	file, err := os.Open("/home/elim/.bashrc")

	if err != nil {
		fmt.Println("读取文件失败", err)
		panic(err)
	}

	// 及时的关闭资源
	defer file.Close()

	// 创建一个带缓冲区的Reader
	reader := bufio.NewReader(file)

	for {
		// 按换行符分隔，读取一行
		line, err := reader.ReadString('\n')
		if err == io.EOF {
			// 当读取到了文件的末尾时err会返回io.EOF
			break
		} else if err != nil {
			// 读取文件失败
			panic(err)
		} else {
			// 输出读取到的那一行数据，因为行数据的末尾已经包含了一个换行符，输出时就不带换行了
			fmt.Print(line)
		}
	}

	fmt.Println("文件读取结束。")

}
