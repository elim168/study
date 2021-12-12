package main

import (
	"flag"
	"fmt"
)

func main() {
	/*
		flag包可以用于获取命令行中命名的参数，如“ls -a”命名的参数就是a。
	*/

	var arg1 string
	var arg2 int
	var arg3 bool

	// 声明一个命名参数arg1（对应第2个参数）,它的值将绑定变量arg1（对应第1个参数）。第三个参数是默认值。第4个参数是参数的说明
	flag.StringVar(&arg1, "arg1", "abc", "这里填写参数arg1的说明。arg1的默认值为abc")
	flag.IntVar(&arg2, "arg2", 123, "整数类型，默认值123")
	flag.BoolVar(&arg3, "arg3", false, "bool类型，默认值为false。指定-arg3即为true，不指定即为false。不需要指定为-arg3 true")

	// 调用Parse()用来解析上面的参数
	flag.Parse()

	// Args()可以获取到其它的非命名参数。命名参数需要声明在其它参数之前。
	otherArgs := flag.Args()

	fmt.Printf("arg1=%v,arg2=%v,arg3=%v。其它参数：%v\n", arg1, arg2, arg3, otherArgs)
}
