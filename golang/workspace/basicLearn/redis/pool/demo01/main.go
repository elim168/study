package main

import (
	"fmt"
	"log"
	"time"

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
}

func main() {

	defer pool.Close()
	conn := pool.Get()
	defer conn.Close()
	_, err := conn.Do("Set", "hello", "world")
	if err != nil {
		log.Fatal("设置结果错误", err)
	}
	value, err := redis.String(conn.Do("get", "hello"))
	if err != nil {
		log.Fatal("获取结果失败", err)
	}
	fmt.Println("取到的结果是：", value)

	fmt.Println(pool)

}
