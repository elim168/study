package model

import (
	"encoding/json"
	"fmt"

	"example.com/elim/basic/project/chatroom/common/message"
	"github.com/garyburd/redigo/redis"
)

var (
	UserDao *userDao
)

type userDao struct {
	pool *redis.Pool
}

func NewUserDao(pool *redis.Pool) (userDaoIns *userDao) {
	userDaoIns = &userDao{
		pool: pool,
	}
	return
}

func (this *userDao) getUserById(userId int) (user User, err error) {
	conn := this.pool.Get()
	defer conn.Close()
	res, err := redis.String(conn.Do("hget", "users", userId))
	if err != nil {
		if err == redis.ErrNil {
			err = ERR_USER_NOT_EXIST
		}
		return
	}
	json.Unmarshal([]byte(res), &user)
	return
}

func (this *userDao) Login(userId int, password string) (user User, err error) {
	user, err = this.getUserById(userId)
	if err != nil {
		fmt.Println("登录错误（查询用户）：", err)
		return
	}
	if user.Password != password {
		err = ERR_USER_PWD_ERR
		return
	}
	return
}

func (this *userDao) Register(user message.User) (err error) {
	_, err = this.getUserById(user.UserId)
	if err == nil {
		// 说明用户已经存在了。
		err = ERR_USER_EXIST
		return
	}

	data, err := json.Marshal(user)
	if err != nil {
		fmt.Println("序列化用户信息失败")
		return
	}
	conn := this.pool.Get()
	defer conn.Close()
	_, err = conn.Do("hset", "users", user.UserId, string(data))
	if err != nil {
		fmt.Println("注册时用户信息写入redis失败")
		return
	}
	return
}
