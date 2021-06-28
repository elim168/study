package main

import (
	"fmt"
	"strconv"
)

// 字符串转数字
func main() {

	// 字符串转换为整数，10进制，32位，即4字节
	a,err := strconv.ParseInt("123", 10, 32)
	if err != nil {
		panic(err)
	}
	fmt.Println(a)

	a,_ = strconv.ParseInt("123", 8, 32)
	fmt.Println("八进制的结果为", a)

	a,_ = strconv.ParseInt("123", 16, 32)
	fmt.Println("16进制的结果为", a)

	// 进制可以填写任意进制，包括自己定义的。最大可以填写36,即使用0-9,a-z的所有组合
	a,_ = strconv.ParseInt("123", 36, 32)
	fmt.Println("36进制的结果为", a)
	a,_ = strconv.ParseInt("1100", 2, 32)
	fmt.Println("二进制的结果为", a)

	// 进制也可以填写0,即表示使用字符串表示的进制。默认是10进制。
	a,_ = strconv.ParseInt("123", 0, 32)
	fmt.Println(a)
	// 0x开头是16进制
	a,_ = strconv.ParseInt("0x123", 0, 32)
	fmt.Println(a)
	// 数字0开头是八进制
	a,_ = strconv.ParseInt("0123", 0, 32)
	fmt.Println(a)

	fmt.Println(strconv.ParseUint("123", 0, 32))
	fmt.Println(strconv.ParseBool("True"))
	fmt.Println(strconv.ParseFloat("56.369", 32))

	// Atoi可以把数字以10进制转换为整数。等价于ParseInt(str, 10, 0)
	fmt.Println(strconv.Atoi("123"))

	// 把数字100转成对应的10进制表示的字符串
	fmt.Println(strconv.FormatInt(100, 10))//100
	// 把数字100转成对应的二进制表示的字符串
	fmt.Println(strconv.FormatInt(100, 2))//1100100


}
