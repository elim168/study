package main

import (
	"fmt"
	"regexp"
)

// 正则表达式示例
func main() {

	// 直接判断字符串是否匹配正则表达式
	matched, err := regexp.Match("He[a-z]+", []byte("Hello"))
	if err == nil {
		fmt.Println(matched)
	}

	regex1, err := regexp.Compile("He[a-z]+")
	if err != nil {
		panic(err)
	}
	fmt.Println(regex1.MatchString("Hello"))
	fmt.Println(regex1.Match([]byte("Hello")))
	fmt.Println(regex1.Match([]byte("Haello")))
	// 只匹配一个
	fmt.Println(regex1.FindString("Hello World Hello Golang"))
	fmt.Println(string(regex1.Find([]byte("Hello World Hello Golang"))))
	fmt.Println(regex1.FindAllString("Hello World Hello Golang", -1))
	fmt.Println(regex1.FindStringIndex("Hello World Hello Golang"))

	r2 := regexp.MustCompile("He([a-z]+)ww")
	// submatch会把正则表达式中小括号包起来的内容匹配的内容也进行输出。
	fmt.Println(r2.FindStringSubmatch("Helloww World Helloww Golang"))//[Helloww llo]
	fmt.Println(r2.FindAllStringSubmatch("Helloww World Helloww Golang", -1))//[[Helloww llo] [Helloww llo]]



}
