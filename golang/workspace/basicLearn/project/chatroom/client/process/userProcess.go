package process

import (
	"encoding/json"
	"fmt"
	"net"

	"example.com/elim/basic/project/chatroom/common/message"
)

var transfer message.Transfer

type UserProcessor struct {
}

var smsProcessor *SmsProcessor

func (this *UserProcessor) Login() {
	var (
		userId   int
		password string
	)

	fmt.Println("请输入用户ID")
	fmt.Scanln(&userId)
	fmt.Println("请输入用户密码")
	fmt.Scanln(&password)

	//1. 连接服务器
	conn, err := net.Dial("tcp", "localhost:8899")
	if err != nil {
		fmt.Println("连接服务器错误", err)
		return
	}

	defer conn.Close()

	var loginMes = message.LoginMes{
		UserId:   userId,
		Password: password,
	}

	data, err := json.Marshal(loginMes)
	if err != nil {
		fmt.Println("json.Marshal失败", err)
		return
	}

	// 外层消息
	var mes = message.Message{
		Type: message.LoginMesType,
		Data: string(data),
	}

	transfer = message.Transfer{Conn: conn}

	transfer.WritePkg(mes)
	resMes, err := transfer.ReadPkg()
	if err != nil {
		fmt.Println("读取响应信息失败，err =", err)
		return
	}
	fmt.Println("响应信息为：", resMes)
	var loginResMes message.LoginResMes
	err = json.Unmarshal([]byte(resMes.Data), &loginResMes)
	if err != nil {
		fmt.Println("反序列化登录响应信息失败，err =", err)
		return
	}
	if loginResMes.Code == 200 {
		smsProcessor = &SmsProcessor{
			Conn: conn,
			User: message.User{
				UserId: userId,
				Name:   loginResMes.Name,
				Status: message.StatusOnline,
			},
		}
		// fmt.Println(loginResMes, "登录成功，当前在线用户列表如下：")
		for _, userId := range loginResMes.OnlineUserIds {
			onlineUsers[userId] = message.User{
				UserId: userId,
				Status: message.StatusOnline,
			}
		}
		// 开启一个协程读取服务端发送的信息
		go serverProcessMes()
		// 循环显示菜单
		for {
			ShowMenu(loginResMes.Name)
		}
	} else {
		fmt.Println("登录失败，原因：", loginResMes.Error)
	}

}

func (this *UserProcessor) Register() {
	var (
		userId   int
		password string
		name     string
	)

	fmt.Println("请输入用户ID")
	fmt.Scanln(&userId)
	fmt.Println("请输入用户密码")
	fmt.Scanln(&password)
	fmt.Println("请输入姓名")
	fmt.Scanln(&name)

	//1. 连接服务器
	conn, err := net.Dial("tcp", "localhost:8899")
	if err != nil {
		fmt.Println("连接服务器错误", err)
		return
	}

	defer conn.Close()
	user := message.User{
		UserId:   userId,
		Password: password,
		Name:     name,
	}

	var registerMes = message.RegisterMes{
		User: user,
	}

	data, err := json.Marshal(registerMes)
	if err != nil {
		fmt.Println("json.Marshal失败", err)
		return
	}

	// 外层消息
	var mes = message.Message{
		Type: message.RegisterMesType,
		Data: string(data),
	}

	transfer = message.Transfer{Conn: conn}

	fmt.Println("准备发送的消息为：", mes, mes.Type, mes.Data)
	transfer.WritePkg(mes)
	resMes, err := transfer.ReadPkg()
	if err != nil {
		fmt.Println("读取响应信息失败，err =", err)
		return
	}
	fmt.Println("响应信息为：", resMes)
	var registerResMes message.RegisterResMes
	err = json.Unmarshal([]byte(resMes.Data), &registerResMes)
	if err != nil {
		fmt.Println("反序列化登录响应信息失败，err =", err)
		return
	}
	if registerResMes.Code == 200 {
		fmt.Println("注册成功，请登录")
	} else {
		fmt.Println("注册失败，原因：", registerResMes.Error)
	}

}
