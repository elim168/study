package main

import (
	"fmt"
	"runtime"
	"time"
)

func main() {

	fmt.Print("Go runs on ")
	switch os := runtime.GOOS; os {
	case "darwin":
		fmt.Println("OS X.")
	case "linux":
		fmt.Println("Linux.")
	default:
		// freebsd, openbsd,
		// plan9, windows...
		fmt.Printf("%s.", os)
	}

	// golang中的switch满足了某一case后会自动跳出该分支
	a := 2
	switch a {
	case 1:
		println("a=1")
	case 2:
		println("a=2")
		// 加了fallthrough后会跳到下一分支
		fallthrough
	case 3:
		println("a=3")
	default:
		println("default a=", a)
	}

	var b int
	b = 1
	// 下面这种写法把switch当作if语句来用
	switch {
	case b > 5:
		println("b > 5")
	case b > 3:
		println("b > 3")
	default:
		println("b=", b)
	}

	switch time.Now().Weekday() {
	// 一个case可以同时包含多种情形
	case time.Saturday, time.Sunday:
		fmt.Println("Today is Weekend!")
	default:
		fmt.Println("Today is Work day!")
	}

	// 定义一个内部函数
	mytype := func (i interface{}) {
		switch t := i.(type) {
		case bool:
			fmt.Println("boolean")
		case int:
			fmt.Println("int")
		case string:
			fmt.Println("string")
		default:
			fmt.Println("unknown type ", t)
		}
	}

	mytype(true)
	mytype(123)
	mytype("hello")
	mytype(12.3)

	abc := true
	fmt.Printf("abc's type is %T", abc)

}



