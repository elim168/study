package main

import (
	"fmt"
	"os"
	"os/exec"
	"syscall"
)

// 本示例演示如何执行系统调用
func main() {


	// 先找到需要执行的命令的路径
	executableFile, err := exec.LookPath("ls")
	if err != nil {
		panic(err)
	}

	// 执行系统调用的参数数组的第一个参数是命令自身
	args := []string{"ls", "-al", "/home"}

	// 执行了系统调用后如果调用成功了，我们的程序就执行到这里就结束了，取而代之的是执行指定的目标程序。
	// 如果执行失败了则会返回错误信息，我们的程序可以继续执行
	err = syscall.Exec(executableFile, args, os.Environ())
	if err != nil {
		fmt.Println("出错了")
		panic(err)
	}

	fmt.Println("执行了系统调用成功后这句话是看不到的，不会执行到这里。")

}
