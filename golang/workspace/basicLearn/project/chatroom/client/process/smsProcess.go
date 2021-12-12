package process

import (
	"encoding/json"
	"fmt"
	"net"

	"example.com/elim/basic/project/chatroom/common/message"
)

type SmsProcessor struct {
	Conn net.Conn
	message.User
}

// 群发消息
func (this *SmsProcessor) sendGroupMes(content string) (err error) {
	smsMes := message.SmsMes{
		Content: content,
		User:    this.User,
	}
	data, err := json.Marshal(smsMes)
	if err != nil {
		fmt.Println("发送消息marshal失败", err)
		return
	}
	mes := message.Message{
		Type: message.SmsMesType,
		Data: string(data),
	}
	transfer := &message.Transfer{
		Conn: this.Conn,
	}
	err = transfer.WritePkg(mes)
	return
}
