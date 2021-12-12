package process

import (
	"encoding/json"
	"fmt"
	"net"

	"example.com/elim/basic/project/chatroom/common/message"
	"example.com/elim/basic/project/chatroom/server/model"
)

type UserProcessor struct {
	Conn   net.Conn
	UserId int
}

// 处理登录消息
func (this *UserProcessor) HandleLogin(mes message.Message) (err error) {
	var loginMes message.LoginMes
	err = json.Unmarshal([]byte(mes.Data), &loginMes)
	if err != nil {
		fmt.Println("解析登录消息失败，err=", err)
		return
	}

	// 登录响应消息
	var loginResMes message.LoginResMes

	user, err := model.UserDao.Login(loginMes.UserId, loginMes.Password)

	if err == model.ERR_USER_NOT_EXIST {
		loginResMes.Code = 203
		loginResMes.Error = err.Error()
	} else if err == model.ERR_USER_PWD_ERR {
		loginResMes.Code = 403
		loginResMes.Error = err.Error()
	} else if err != nil {
		loginResMes.Code = 503
		loginResMes.Error = err.Error()
	} else {
		// 登录成功
		loginResMes.Code = 200
		loginResMes.Name = user.Name
		fmt.Println(user, "登录成功")
		this.UserId = user.UserId
		userManager.AddOnlineUser(this)
		loginResMes.OnlineUserIds = userManager.GetAllOnlineUserId()
		// 通知其它用户当前用户上线了
		this.NotifyOthersOnline()
	}

	// 序列化登录响应消息
	data, err := json.Marshal(loginResMes)
	if err != nil {
		fmt.Println("序列化登录响应消息失败，err =", err)
		return
	}

	// 响应消息
	var resMes message.Message
	resMes.Type = message.LoginResMesType
	resMes.Data = string(data)

	transfer := &message.Transfer{Conn: this.Conn}

	transfer.WritePkg(resMes)
	return

}

// 处理注册消息
func (this *UserProcessor) HandleRegister(mes message.Message) (err error) {
	var registerMes message.RegisterMes
	err = json.Unmarshal([]byte(mes.Data), &registerMes)
	if err != nil {
		fmt.Println("解析注册消息失败，err=", err)
		return
	}

	// 登录响应消息
	var registerResMes message.RegisterResMes

	err = model.UserDao.Register(registerMes.User)

	if err == model.ERR_USER_EXIST {
		registerResMes.Code = 303
		registerResMes.Error = err.Error()
	} else if err != nil {
		registerResMes.Code = 503
		registerResMes.Error = err.Error()
	} else {
		// 注册成功
		registerResMes.Code = 200
		fmt.Println(registerMes.User, "注册成功")
	}

	// 序列化登录响应消息
	data, err := json.Marshal(registerResMes)
	if err != nil {
		fmt.Println("序列化注册用户的响应消息失败，err =", err)
		return
	}

	// 响应消息
	var resMes message.Message
	resMes.Type = message.RegisterResMesType
	resMes.Data = string(data)

	transfer := &message.Transfer{Conn: this.Conn}

	transfer.WritePkg(resMes)
	return

}

// 通知其它用户当前用户上线了
func (this *UserProcessor) NotifyOthersOnline() {
	currentUserId := this.UserId
	onlineUsers := userManager.GetAllOnlineUser()
	for userId, up := range onlineUsers {
		// 当前用户不需要再通知了。（不需要通知自己）
		if userId == currentUserId {
			continue
		}
		up.NotifyMeOnline(currentUserId)
	}
}

// 其它用户上线后需要通知当前用户
func (this *UserProcessor) NotifyMeOnline(userId int) {

	var statusMes message.NotifyUserStatusMes
	statusMes.UserId = userId
	statusMes.Status = message.StatusOnline

	data, err := json.Marshal(statusMes)
	if err != nil {
		fmt.Println("序列化statusMes失败，err =", err)
		return
	}

	// 响应消息
	var mes message.Message
	mes.Type = message.NotifyUserStatusMesType
	mes.Data = string(data)

	transfer := &message.Transfer{Conn: this.Conn}

	transfer.WritePkg(mes)

}
