package main

import (
	"fmt"
	"log"

	"github.com/garyburd/redigo/redis"
)

func main() {
	conn, err := redis.Dial("tcp", "172.17.0.3:6379")
	if err != nil {
		log.Fatal("连接Redis失败", err)
	}
	defer conn.Close()
	err = conn.Send("set", "key1", "value1")
	if err != nil {
		fmt.Println("命令发送失败", err)
		return
	}
	reply, err := conn.Do("get", "key1")
	if err != nil {
		fmt.Println("获取key1失败", err)
		return
	}
	// 响应结果是interface{}类型，实际类型是[]uint8
	value1 := reply.([]uint8)
	fmt.Println(string(value1))

	// Do()方法的结果本来是interface{},error，redis.String()把结果interface{}转为了string，error不变。
	ok, err := redis.String(conn.Do("set", "key2", "value2~你好"))
	if err != nil {
		fmt.Println("设置key2失败", err)
		return
	}
	if ok == "OK" {
		fmt.Println("设置key2成功")
	}
	key2Value, err := redis.String(conn.Do("get", "key2"))
	fmt.Println("key2的值是：", key2Value)

	// 多值一起设置
	_, err = conn.Do("mset", "key3", "value3", "key4", "value4", "key5", "value5")
	if err != nil {
		fmt.Println("多值设置失败", err)
		return
	}

	// 使用strings()可以把响应结果中的多个值封装为一个string切片
	values, err := redis.Strings(conn.Do("mget", "key1", "key2", "key3", "key4", "key5"))
	if err != nil {
		fmt.Println("获取多个值失败", err)
		return
	}
	for i, value := range values {
		fmt.Printf("values[%d]=%v\n", i, value)
	}

}
