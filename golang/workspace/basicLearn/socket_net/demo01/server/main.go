package main

import (
	"fmt"
	"io"
	"net"
)

func main() {
	// tcp指定使用的是tcp协议，也可以指定其它的，如tcp4/tcp6/udp等
	// localhost:8899指定监听的IP地址和端口，IP地址和端口都是可选的。没有指定IP地址则监听当前机器上的所有IP，没有指定端口则会随机选择一个端口
	// listener, err := net.Listen("tcp", "localhost:8899")
	listener, err := net.Listen("tcp", ":8899")
	if err != nil {
		fmt.Println("服务器监听失败", err)
		return
	}
	fmt.Println("服务器监听成功，监听地址是：", listener.Addr())
	fmt.Println("服务器准备等待客户端的连接……")
	for {
		conn, err := listener.Accept()
		if err != nil {
			fmt.Println("接收到一个客户端的连接，但是连接失败。", err)
		} else {
			fmt.Println("接收到一个客户端的连接，连接成功，地址是：", conn.RemoteAddr())
			// 单独启动一个协程处理该连接
			go handle(conn)
		}
	}
}

func handle(conn net.Conn) {
	// 先在退出方法前关闭连接
	defer conn.Close()
	fmt.Printf("服务端正在等待客户端[%v]的输入，服务端地址是：%v\n", conn.RemoteAddr(), conn.LocalAddr())
	buf := make([]byte, 1024)
	for {
		n, err := conn.Read(buf)
		if err == io.EOF {
			fmt.Printf("客户端[%v]退出了EOF\n", conn.RemoteAddr())
			return
		} else if err != nil {
			fmt.Printf("服务端读取客户端[%v]的输入出错, err = %v \n", conn.RemoteAddr(), err)
			return
		}
		msg := string(buf[:n])
		fmt.Printf("从客户端[%v]读取到了%d个字节的数据：%v \n", conn.RemoteAddr(), n, msg)
		if msg == "bye\n" {
			fmt.Printf("客户端[%v]退出了 \n", conn.RemoteAddr())
		}
	}
}
