package main

import (
	"bufio"
	"fmt"
	"log"
	"net"
	"os"
)

func main() {
	// 以tcp协议连接到本机的8899端口
	conn, err := net.Dial("tcp", "localhost:8899")
	if err != nil {
		log.Fatal("连接远程服务器出错", err)
	}
	// 关闭连接
	defer conn.Close()
	fmt.Printf("客户端连接远程服务器[%v]成功\n", conn.RemoteAddr())
	reader := bufio.NewReader(os.Stdin)
	for {
		line, err := reader.ReadString('\n')
		if err != nil {
			log.Fatal("从标准输入读取数据失败")
		}
		n, err := conn.Write([]byte(line))
		if err != nil {
			log.Fatal("发送数据到远程服务器失败", err)
		}
		fmt.Printf("发送了%d个字节到远程服务器\n", n)
		// 去掉最后的换行符
		line = string(line[:n-1])
		if line == "bye" {
			return
		}
	}

}
