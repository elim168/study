package greetings

import (
	"fmt"
	"math/rand"
	"time"
        "errors"
)

func Hello(name string) (string, error) {

	// 没有传递参数name时返回一个错误
	if name == "" {
		return "", errors.New("empty name")
	}
	message := fmt.Sprintf("你好，%v。欢迎你", name)
	return message,nil

}



func HelloRand(name string) (string, error) {

        // 没有传递参数name时返回一个错误
        if name == "" {
                return "", errors.New("empty name")
        }
        message := fmt.Sprintf(randomFormat(), name)
        return message,nil

}


func Hellos(names []string) (map[string]string, error) {

	messages := make(map[string]string)
	for _,name := range names {
		message, err := HelloRand(name)
		if err != nil {
			return nil, err
		}
		messages[name] = message
	}
	return messages, nil

}

// init go语言的包初始化函数，会在包变量初始化之后执行
func init() {
	rand.Seed(time.Now().UnixNano())
}

func randomFormat() string {
	formats := []string{
		"Hi,%v.Welcome!",
		"Great to see you,%v!",
		"Hail,%v! Well met!",
	}
	return formats[rand.Intn(len(formats))]
}


