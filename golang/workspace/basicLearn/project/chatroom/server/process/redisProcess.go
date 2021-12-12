package process

import (
	"time"

	"example.com/elim/basic/project/chatroom/server/model"
	"github.com/garyburd/redigo/redis"
)

var pool *redis.Pool

func init() {
	pool = &redis.Pool{
		MaxIdle: 10,
		// Maximum number of connections allocated by the pool at a given time.
		// When zero, there is no limit on the number of connections in the pool.
		MaxActive:   20,
		IdleTimeout: time.Second * 60,
		Dial: func() (redis.Conn, error) {
			return redis.Dial("tcp", "172.17.0.3:6379")
		},
	}

	// 初始化UserDao
	model.UserDao = model.NewUserDao(pool)

}
