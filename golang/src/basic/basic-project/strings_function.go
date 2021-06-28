package main

import (
	"fmt"
)

// 给引入的strings取一个别名
import s "strings"

// 给fmt.Println取一个别名
var p = fmt.Println

func main() {

	p("Compare", s.Compare("ABC", "DEF"))
	p("contains", s.Contains("Hello World", "Hello"))
	// 只要包含其中任意一个字符即输出true
	p("ContainsAny", s.ContainsAny("Hello World!", "abc"))
	p("Count", s.Count("Hello World!", "l"))
	p("EqualFold", s.EqualFold("Hello", "Hello1"))
	p("EqualFold", s.EqualFold("Hello", "Hello"))
	p("Fields", s.Fields("H e l l o"))
	p("HasPrefix", s.HasPrefix("Hello", "He"))
	p("HasSuffix", s.HasSuffix("Hello", "lo"))
	p("Index", s.Index("Hello", "lo"))
	p("Join", s.Join([]string {"Hello", "World", "Golang"}, "||"))
	p("LastIndex", s.LastIndex("Hello", "l"))
	p("Repeat", s.Repeat("Hello", 10))
	p("Replace", s.Replace("HelloHello", "l", "L", 2))
	p("ReplaceAll", s.ReplaceAll("HelloHello", "l", "L"))
	p("Split", s.Split("Hello,World,Golang", ","))
	p("SplitN", s.SplitN("Hello,World,Golang", ",", 2))
	// 它截取后会包含逗号，而split不会。逗号会紧跟前一部分
	p("SplitAfter", s.SplitAfter("Hello,World,Golang", ","))
	p("SplitAfterN", s.SplitAfterN("Hello,World,Golang", ",", 2))
	p("ToLower", s.ToLower("Hello,World,Golang"))
	p("ToUpper", s.ToUpper("Hello,World,Golang"))
	// 会把头尾上的i和!都删掉
	p("Trim", s.Trim("iiiiHello!!!!", "i!"))
	p("TrimPrefix", s.TrimPrefix("iiiiHello!!!!", "ii"))
	p("TrimSuffix", s.TrimSuffix("iiiiHello!!!!", "!!"))
	p("TrimLeft", s.TrimLeft("iiiiHello!!!!", "i!!"))
	p("TrimRight", s.TrimRight("iiiiHello!!!!", "!!"))
	p("TrimSpace", s.TrimSpace(" Hello "))



	// 字符串可以按索引访问字符串中的每一个字母
	str := "Hello World!"
	for i := 0; i < len(str); i++ {
		p("Char", str[i])
	}

	// 字符串转字节数组
	b := []byte("Hello")
	// 字节数组转字符串
	str2 := string(b)
	fmt.Println(b, str2)

	// 取字节数组的长度。所以输出是6
	fmt.Println(len("中文"))


}
