package main

import (
	"fmt"
	"os"

	"example.com/elim/basic/project/chatroom/client/process"
)

func main() {
	// 选择的菜单
	var menuItem int
	for {
		fmt.Println("---------------------欢迎访问聊天室系统-------------------------------")
		fmt.Println("----------请选择菜单")
		fmt.Println("1. 登录聊天室")
		fmt.Println("2. 注册用户")
		fmt.Println("3. 退出")

		fmt.Scanln(&menuItem)

		switch menuItem {
		case 1:
			userProcessor := &process.UserProcessor{}
			userProcessor.Login()
		case 2:
			userProcessor := &process.UserProcessor{}
			userProcessor.Register()
		case 3:
			fmt.Println("选择了退出")
			os.Exit(0)
		default:
			fmt.Println("您的输入有误，请重新输入")
		}

	}

}
