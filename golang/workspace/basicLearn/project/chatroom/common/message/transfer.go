package message

import (
	"encoding/binary"
	"encoding/json"
	"errors"
	"fmt"
	"net"
	"strconv"
)

type Transfer struct {
	Conn net.Conn
	Buf  [4096]byte
}

// 发送数据
func (this *Transfer) WritePkg(mes Message) (err error) {
	data, err := json.Marshal(mes)
	if err != nil {
		fmt.Println("json.Marshal失败", err)
		return
	}

	dataLen := len(data)
	// 把数字转为字节数组
	binary.BigEndian.PutUint32(this.Buf[:4], uint32(dataLen))

	// 发送数据的长度
	_, err = this.Conn.Write(this.Buf[:4])
	if err != nil {
		fmt.Println("发送数据长度失败，err =", err)
		return
	}
	_, err = this.Conn.Write(data)
	if err != nil {
		fmt.Println("发送数据失败，err =", err)
		return
	}
	return
}

// 读取数据
func (this *Transfer) ReadPkg() (message Message, err error) {
	n, err := this.Conn.Read(this.Buf[:4])
	if err != nil {
		return
	}
	if n != 4 {
		fmt.Println("接收的数据长度错误，n=", n)
		err = errors.New("接收的数据长度错误，期望是4,实际是" + strconv.Itoa(n))
		return
	}
	// 数据包的长度
	pkgLen := int(binary.BigEndian.Uint32(this.Buf[:n]))
	n, err = this.Conn.Read(this.Buf[:pkgLen])
	if err != nil {
		return
	}
	if n != pkgLen {
		err = errors.New(fmt.Sprintf("数据包长度读取错误，期望是%d，实际是%d", pkgLen, n))
		return
	}
	json.Unmarshal(this.Buf[:pkgLen], &message)
	return
}
