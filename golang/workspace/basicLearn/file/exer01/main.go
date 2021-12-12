package main

import (
	"bufio"
	"fmt"
	"io"
	"log"
	"math"
	"os"
)

type Counter struct {
	Char    int
	Number  int
	Space   int
	Chinese int
	Other   int
}

func main() {
	fileName := "/home/elim/.bashrc"

	file, err := os.Open(fileName)
	if err != nil {
		log.Fatal(err)
	}
	defer file.Close()

	var counter Counter

	reader := bufio.NewReader(file)
	for {
		line, err := reader.ReadString('\n')
		if err == io.EOF {
			break
		}
		for _, char := range line {
			switch {
			case char >= 'a' && char <= 'z':
				fallthrough
			case char >= 'A' && char <= 'Z':
				counter.Char++
			case char >= '0' && char <= '9':
				counter.Number++
			case char == ' ' || char == '\t':
				counter.Space++
			case char >= math.MaxInt8:
				// 英文字母和数字一个字节就可以表示了。所以简单的说超过一个字节的就表示中文
				counter.Chinese++
			default:
				counter.Other++
			}
		}
	}

	fmt.Printf("统计的文件[%v]中各种字符的个数为%+v \n", fileName, counter)

}
