package process

import "fmt"

var userManager UserManager

type UserManager struct {
	OnlineUsers map[int]*UserProcessor
}

func init() {
	userManager = UserManager{make(map[int]*UserProcessor)}
}

func GetUserManager() *UserManager {
	return &userManager
}

// 增加在线用户
func (this *UserManager) AddOnlineUser(userProcessor *UserProcessor) {
	this.OnlineUsers[userProcessor.UserId] = userProcessor
}

// 删除在线用户（用户下线）
func (this *UserManager) DelOnlineUser(userId int) {
	delete(this.OnlineUsers, userId)
}

// 获取所有的在线用户
func (this *UserManager) GetAllOnlineUser() map[int]*UserProcessor {
	return this.OnlineUsers
}

// 获取所有在线用户的userId
func (this *UserManager) GetAllOnlineUserId() []int {
	var onlineUserIds []int
	for userId, _ := range this.OnlineUsers {
		onlineUserIds = append(onlineUserIds, userId)
	}
	return onlineUserIds
}

// 获取在线的某个用户
func (this *UserManager) GetOnlineUserById(userId int) (up *UserProcessor, err error) {
	up, ok := this.OnlineUsers[userId]
	if !ok {
		err = fmt.Errorf("userId=%d的用户不在线", userId)
	}
	return
}
