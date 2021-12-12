package process

import (
	"fmt"

	"example.com/elim/basic/project/chatroom/common/message"
)

var onlineUsers map[int]message.User = make(map[int]message.User)

// 更新在线用户的信息
func updateOnlineUser(notifyUserStatusMes message.NotifyUserStatusMes) {
	userId := notifyUserStatusMes.UserId
	user, ok := onlineUsers[userId]
	if !ok {
		onlineUsers[userId] = message.User{
			UserId: userId,
		}
	}
	user.Status = message.StatusOnline
	showOnlineUser()
}

// 展示当前的在线用户列表
func showOnlineUser() {
	fmt.Println("在线用户列表如下：")
	for userId, _ := range onlineUsers {
		fmt.Println(userId)
	}
}
