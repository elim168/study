package main

import (
    "fmt"
    "log"
    "example.com/greetings"
)


func hello() {
//// Set properties of the predefined Logger, including
    // the log entry prefix and a flag to disable printing
    // the time, source file, and line number.
    log.SetPrefix("greetings:")
    log.SetFlags(0)
    // Get a greeting message and print it.
    message,err := greetings.Hello("Gladys")
    fmt.Println(message)

        message,err = greetings.Hello("")
        if err != nil {
                // 如果有错误，log.Fatal()会打印日志且会中断程序的执行
                log.Fatal(err)
        }
        fmt.Println(message, "第二次")

}

func main() {
//// Set properties of the predefined Logger, including
    // the log entry prefix and a flag to disable printing
    // the time, source file, and line number.
    log.SetPrefix("greetings:")
    log.SetFlags(0)
    // Get a greeting message and print it.
    message,err := greetings.Hello("Gladys")
    fmt.Println(message)

	message,err = greetings.HelloRand("张三")
	if err != nil {
		// 如果有错误，log.Fatal()会打印日志且会中断程序的执行
		log.Fatal(err)
	}
	fmt.Println(message)



	// 同时问候多个人
	names := []string{"ZhangSan", "Andy", "July", "August"}
	messages, err := greetings.Hellos(names)
	if err != nil {
		log.Fatal(err)
	}
	fmt.Println(messages)

}
