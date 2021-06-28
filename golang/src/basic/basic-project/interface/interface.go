package main

import "fmt"

// 定义的一个接口
type log interface {
	info(msg string)
	warn(msg string)
	error(msg string)
	getLevel() string
}

type Stdout struct {

	level string

}

// Stdout对log接口的实现
func (s Stdout) info(msg string) {
	if s.getLevel() == "info" {
		fmt.Println("info:", msg)
	} else {
		fmt.Println("info omit:", msg)
	}
}

func (s Stdout) warn(msg string) {
	if s.getLevel() == "warn" {
		fmt.Println("warn:", msg)
	} else {
		fmt.Println("warn omit:", msg)
	}
}

func (s Stdout) error(msg string) {
	if s.getLevel() == "error" {
		fmt.Println("error:", msg)
	} else {
		fmt.Println("error omit:", msg)
	}
}

func (s Stdout) getLevel() string {
	return s.level
}

func main() {


	logger := Stdout{"info"}
	logger.info("Hello info")
	logger.warn("Hello warn")
	logger.error("hello error")

	logger.level = "warn"
	logger.info("Hello info")
	logger.warn("Hello warn")
	logger.error("hello error")

	// 空接口能够保存任意类型的值
	var i interface{}
	i = 10
	fmt.Println(i)
	i = "Hello World"
	fmt.Println(i)


}
