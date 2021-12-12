package process

import (
	"encoding/json"
	"fmt"
	"net"

	"example.com/elim/basic/project/chatroom/common/message"
)

type SmsProcessor struct {
}

// 转发群聊的消息
func (this *SmsProcessor) SendGroupMes(mes message.Message) {
	var smsMes message.SmsMes
	json.Unmarshal([]byte(mes.Data), &smsMes)
	users := userManager.GetAllOnlineUser()
	for userId, up := range users {
		if userId == smsMes.UserId {
			// 不发给自己
			continue
		}
		this.fowardGroupMes(mes, up.Conn)
	}
}

// 转发消息给某个在线的用户
func (this *SmsProcessor) fowardGroupMes(mes message.Message, conn net.Conn) {
	tf := &message.Transfer{Conn: conn}
	err := tf.WritePkg(mes)
	if err != nil {
		fmt.Println("转发消息失败，err=", err)
	}
}
