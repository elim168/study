package main

import (
	"fmt"
	"net/http"
)

// 处理器函数。方法签名必须保持这样，方法名和参数名可以随意
func handler(w http.ResponseWriter, r *http.Request) {
	fmt.Fprintln(w, "Hello World! Current Request Path is :", r.URL.Path)
}

func main() {
	// 指定访问路径对应的处理器函数
	http.HandleFunc("/", handler)
	// 监听8999端口，然后程序会在这一步卡住
	http.ListenAndServe("localhost:8999", nil)

}
