package process

import (
	"encoding/json"
	"fmt"
	"os"

	"example.com/elim/basic/project/chatroom/common/message"
)

func ShowMenu(name string) {
	fmt.Printf("---------------欢迎%s登录----------------\n", name)
	fmt.Println("1. 查看用户列表")
	fmt.Println("2. 发送信息")
	fmt.Println("3. 信息列表")
	fmt.Println("4. 退出系统")
	fmt.Println("请选择1-4：")
	var key int
	fmt.Scanf("%d\n", &key)
	switch key {
	case 1:
		showOnlineUser()
	case 2:
		fmt.Println("请输入需要发送的消息：")
		var content string
		fmt.Scanln(&content)
		smsProcessor.sendGroupMes(content)
	case 3:
		fmt.Println("信息列表")
	case 4:
		fmt.Println("您选择了退出系统，正在退出")
		os.Exit(0)
	default:
		fmt.Println("您的选择有误，请重新选择")
	}
}

func serverProcessMes() {
	fmt.Println("客户端正在读取服务端发送的消息……")
	for {
		mes, err := transfer.ReadPkg()
		if err != nil {
			fmt.Println("客户端读取服务端信息失败，err=", err)
			return
		}
		switch mes.Type {
		case message.NotifyUserStatusMesType:
			fmt.Println()
			var notifyUserStatusMes message.NotifyUserStatusMes
			err = json.Unmarshal([]byte(mes.Data), &notifyUserStatusMes)
			if err != nil {
				fmt.Println("unmarshal通知用户状态的信息失败")
			} else {
				updateOnlineUser(notifyUserStatusMes)
			}
		case message.SmsMesType:
			showGroupMes(mes)
		default:
			fmt.Println("无法处理的消息")
		}
	}
}

// 展示群发的消息
func showGroupMes(mes message.Message) {
	var smsMes message.SmsMes
	json.Unmarshal([]byte(mes.Data), &smsMes)
	fmt.Printf("用户：%d对大家说：%s \n", smsMes.UserId, smsMes.Content)
}
