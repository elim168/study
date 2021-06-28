package main

import (
	"fmt"
	"net/url"
)

func main() {

	// We'll parse this example URL, which includes a
	// scheme, authentication info, host, port, path,
	// query params, and query fragment.
	s := "postgres://user:pass@host.com:5432/path?k=v&k2=v2#f"

	u,err := url.Parse(s)
	if err != nil {
		panic(err)
	}
	fmt.Println(u.Scheme)
	// Host是包括hostname和端口的
	fmt.Println(u.Host)
	fmt.Println(u.Hostname())
	fmt.Println(u.Port())
	// 路径信息，/path
	fmt.Println(u.Path)
	// 查询参数，map表示。map[k:[v] k2:[v2]]
	fmt.Println(u.Query())
	// k=v&k2=v2
	fmt.Println(u.RawQuery)
	fmt.Println(url.ParseQuery(u.RawQuery))
	// 获取用户密码对象
	fmt.Println(u.User)
	fmt.Println(u.User.Username())
	password, setted := u.User.Password()
	if setted {
		fmt.Println("密码为", password)
	}
	// #后面的部分，即f
	fmt.Println(u.Fragment)
	// 基本等价于parse
	fmt.Println(url.ParseRequestURI("https://www.baidu.com/a/b/c/e.html?k1=v1&k2=v2#123"))

}
