package greetings

import "fmt"

func Hello(name string) string {

	message := fmt.Sprintf("你好，%v。欢迎你", name)
	return message

}
