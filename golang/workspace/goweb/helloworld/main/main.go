package main

import (
	"fmt"
	"net/http"
)

// 处理器函数
func handler(w http.ResponseWriter, r *http.Request) {
	fmt.Fprintln(w, "Hello World! Current Request Path is :", r.URL.Path)
	fmt.Fprintln(w, "包含查询参数URL是：", r.URL)
	fmt.Fprintln(w, "不包含问号的查询参数是：", r.URL.RawQuery)
	fmt.Fprintln(w, "Request的相关信息：", r.Method, r)
	// Header是map[string][]string类型，即头信息的值是切片类型
	fmt.Fprintln(w, "头信息：", r.Header)
	fmt.Fprintln(w, "头信息中的Accept-Encoding：", r.Header["Accept-Encoding"])
	fmt.Fprintln(w, "头信息中的具体属性也可以通过r.Header.Get(\"属性名\")获取：", r.Header.Get("Accept"))
}

func main() {
	fmt.Println("正在准备启动服务器……")
	http.HandleFunc("/", handler)
	// 监听8999端口，然后程序会在这一步卡住
	http.ListenAndServe("localhost:8999", nil)

}
