package main

import (
	"fmt"
	log2 "log"
	"os/exec"
)

// 本示例演示如何调用外部程序
func main() {

	command := exec.Command("ls", "-a", "/home")
	//  A Cmd cannot be reused after calling its Run, Output or CombinedOutput
	// Start()运行了指令后不会等待返回结果
	//command.Start()
	// Run()运行了指令后会等待指令运行完成。底层调用了Start()，之后再调用了Wait()进行等待。
	//err := command.Run()
	// Output()运行了指令后会获取指令的结果
	output, err := command.Output()
	if err != nil {
		panic(err)
	}
	fmt.Println("命令执行后的结果为：", string(output))
	log2.Fatalf("HHHHHHH")

}
