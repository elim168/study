package processor

import (
	"errors"
	"fmt"
	"io"
	"net"

	"example.com/elim/basic/project/chatroom/common/message"
	"example.com/elim/basic/project/chatroom/server/process"
)

type Processor struct {
	Conn   net.Conn
	UserId int
}

func (this *Processor) Process() {
	transfer := &message.Transfer{Conn: this.Conn}
	for {
		mes, err := transfer.ReadPkg()
		if err == io.EOF {
			fmt.Println("客户端退出了")
			_, ok := process.GetUserManager().OnlineUsers[this.UserId]
			if ok {
				process.GetUserManager().DelOnlineUser(this.UserId)
				fmt.Printf("用户-%d下线了\n", this.UserId)
			}
			return
		} else if err != nil {
			fmt.Println("读取数据错误，err =", err)
			return
		}
		fmt.Println("读取到的信息为：", mes)
		err = this.processMessage(mes)
		if err != nil {
			fmt.Println("消息处理失败，err =", err)
			return
		}
	}
}

func (this *Processor) processMessage(mes message.Message) (err error) {
	switch mes.Type {
	case message.LoginMesType:
		userProcessor := &process.UserProcessor{Conn: this.Conn}
		// 处理登录消息
		err = userProcessor.HandleLogin(mes)
		if err == nil {
			this.UserId = userProcessor.UserId
		}
	case message.RegisterMesType:
		userProcessor := &process.UserProcessor{Conn: this.Conn}
		// 处理注册消息
		err = userProcessor.HandleRegister(mes)
	case message.SmsMesType:
		//fmt.Println("收到了群发的消息：", mes)
		smsProcessor := &process.SmsProcessor{}
		smsProcessor.SendGroupMes(mes)
	default:
		fmt.Println("读取到的消息类型为：", mes.Type)
		err = errors.New("消息上送错误，不能处理")
	}
	return
}
