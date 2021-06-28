package main

import (
	"flag"
	"fmt"
)

// ls -a 中的-a就是一个flag参数。golang中的flag包可以让我们很方便的访问命令行中带的flag参数。
// flag参数支持整数、小数、字符串、bool类型
func main() {

	flag.Arg(0)
	// 定义了该命令支持一个名为abool的flag参数，bool类型，默认为false。后面的"一个bool类型的参数，默认false"表示该参数的用法，含义。
	// 该参数的值赋值给了变量abool，它对应的是一个指针
	abool := flag.Bool("abool", false, "一个bool类型的参数，默认false")

	aint64 := flag.Int64("aint64", 10, "一个aint64参数，默认10")
	aint := flag.Int("aint", 1, "一个int类型的参数，默认1")
	astring := flag.String("astring", "Hello", "一个字符串类型参数，默认值为Hello")

	var afloat float64
	// 还可以调用对象的xxxVar方法，传入解析后的flag参数需要赋值的指针变量。这样jiexii的参数就会赋值给对应的指针。
	flag.Float64Var(&afloat, "afloat", 1.23, "一个float64类型的参数")

	// flag参数定义完成后需要调用flag.Parse()进行flag的参数解析，之后我们就可以使用flag参数了。
	flag.Parse()


	fmt.Println("abool:", *abool)
	fmt.Println("aint64:", *aint64)
	fmt.Println("aint:", *aint)
	fmt.Println("astring:", *astring)
	fmt.Println("afloat:", afloat)
	// 剩余参数也可以按照位置来获取，比如取第一个则用flag.Args(0)
	fmt.Println("剩余参数：", flag.Args())

	/**
	使用-h参数即可看到该命令的使用方法。
	$ go run command_line_flags.go -h
	Usage of /tmp/go-build2250005231/b001/exe/command_line_flags:
	  -abool
	    	一个bool类型的参数，默认false
	  -afloat float
	    	一个float64类型的参数 (default 1.23)
	  -aint int
	    	一个int类型的参数，默认1 (default 1)
	  -aint64 int
	    	一个aint64参数，默认10 (default 10)
	  -astring string
	    	一个字符串类型参数，默认值为Hello (default "Hello")
	*/

	/**
	直接运行都取默认值，如下：
	abool: false
	aint64: 10
	aint: 1
	astring: Hello
	afloat: 1.23
	剩余参数： []

	$ go run command_line_flags.go -abool
	abool: true
	aint64: 10
	aint: 1
	astring: Hello
	afloat: 1.23
	剩余参数： []

	$ go run command_line_flags.go -abool -aint64 123 -aint 66 -astring "Hello World" -afloat 111.23
	abool: true
	aint64: 123
	aint: 66
	astring: Hello World
	afloat: 111.23
	剩余参数： []

	$ go run command_line_flags.go -aint 123 abc dee
	abool: false
	aint64: 10
	aint: 123
	astring: Hello
	afloat: 1.23
	剩余参数： [abc dee]

	// flag参数必须都一起指定，指定了非flag参数后之后再自定flag参数也会被当做为其它参数。如下面的-astring hi
	$ go run command_line_flags.go -aint 123 abc dee -astring hi
	abool: false
	aint64: 10
	aint: 123
	astring: Hello
	afloat: 1.23
	剩余参数： [abc dee -astring hi]

	*/

}
