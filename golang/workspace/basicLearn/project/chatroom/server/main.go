package main

import (
	"fmt"
	"log"
	"net"

	"example.com/elim/basic/project/chatroom/server/processor"
)

func main() {
	port := 8899
	fmt.Printf("服务器准备监听在端口%d \n", port)
	address := fmt.Sprintf("localhost:%d", port)
	listener, err := net.Listen("tcp", address)
	if err != nil {
		fmt.Println("net.Listen失败", err)
		return
	}

	fmt.Println("服务器监听成功……")

	for {
		fmt.Println("服务器正在等待客户端的连接……")
		conn, err := listener.Accept()
		if err != nil {
			log.Println("接收客户端连接失败", err)
			continue
		}

		// 启动一个协程处理该客户端连接
		go handle(conn)

	}

}

func handle(conn net.Conn) {

	defer conn.Close()

	fmt.Println("接收到一个客户端连接", conn.RemoteAddr())
	mainProcessor := &processor.Processor{Conn: conn}
	mainProcessor.Process()

}
